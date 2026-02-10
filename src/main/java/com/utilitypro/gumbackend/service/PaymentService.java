package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.domain.entity.Payment;
import com.utilitypro.gumbackend.domain.entity.PaymentAllocation;
import com.utilitypro.gumbackend.domain.entity.UtilityBill;
import com.utilitypro.gumbackend.repository.AuditLogRepository;
import com.utilitypro.gumbackend.repository.PaymentAllocationRepository;
import com.utilitypro.gumbackend.repository.PaymentRepository;
import com.utilitypro.gumbackend.repository.UtilityBillRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Payment Service
 * Handles payment CRUD and bill allocation management
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentAllocationRepository paymentAllocationRepository;
    private final UtilityBillRepository utilityBillRepository;
    private final AuditLogRepository auditLogRepository;
    private final AuthorizationHelper authorizationHelper;

    @Transactional(readOnly = true)
    public List<Payment> listPayments() {
        return paymentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Payment getPayment(UUID id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        
        // Load allocations
        List<PaymentAllocation> allocations = paymentAllocationRepository.findByPaymentId(id);
        // Note: In real implementation, set allocations on payment entity
        
        return payment;
    }

    @Transactional
    public Payment createPayment(Payment payment, List<PaymentAllocation> allocations) {
        authorizationHelper.requireRole("system_admin", "mof_admin");

        // Validate total payment amount = sum of allocations
        BigDecimal allocationSum = allocations.stream()
                .map(PaymentAllocation::getAllocatedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (payment.getAmount().compareTo(allocationSum) != 0) {
            throw new IllegalArgumentException("Payment amount must equal sum of allocations");
        }

        Payment saved = paymentRepository.save(payment);

        // Save allocations and update bill paid_flag
        for (PaymentAllocation allocation : allocations) {
            allocation.setPayment(saved); // Set relationship instead of ID
            paymentAllocationRepository.save(allocation);
            
            // Update bill paid_flag if fully allocated
            UUID billId = allocation.getBill() != null ? allocation.getBill().getId() : null;
            if (billId != null) {
                updateBillPaidFlag(billId);
            }
        }

        createAuditLog("Payment", saved.getId(), "CREATE");
        return saved;
    }

    @Transactional
    public Payment updatePayment(UUID id, Payment updatedPayment, List<PaymentAllocation> newAllocations) {
        authorizationHelper.requireRole("system_admin", "mof_admin");

        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        // Get old allocations to update affected bills
        List<PaymentAllocation> oldAllocations = paymentAllocationRepository.findByPaymentId(id);
        
        // Delete old allocations
        paymentAllocationRepository.deleteByPaymentId(id);

        // Validate new allocations
        BigDecimal allocationSum = newAllocations.stream()
                .map(PaymentAllocation::getAllocatedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (updatedPayment.getAmount().compareTo(allocationSum) != 0) {
            throw new IllegalArgumentException("Payment amount must equal sum of allocations");
        }

        existing.setAmount(updatedPayment.getAmount());
        existing.setPaymentDate(updatedPayment.getPaymentDate());
        existing.setReferenceNumber(updatedPayment.getReferenceNumber());

        Payment saved = paymentRepository.save(existing);

        // Save new allocations
        for (PaymentAllocation allocation : newAllocations) {
            allocation.setPayment(saved); // Set relationship instead of ID
            paymentAllocationRepository.save(allocation);
            UUID billId = allocation.getBill() != null ? allocation.getBill().getId() : null;
            if (billId != null) {
                updateBillPaidFlag(billId);
            }
        }

        // Update old bills that are no longer allocated
        for (PaymentAllocation oldAllocation : oldAllocations) {
            UUID billId = oldAllocation.getBill() != null ? oldAllocation.getBill().getId() : null;
            if (billId != null) {
                updateBillPaidFlag(billId);
            }
        }

        createAuditLog("Payment", saved.getId(), "UPDATE");
        return saved;
    }

    @Transactional
    public void deletePayment(UUID id) {
        authorizationHelper.requireRole("system_admin");

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        // Get allocations to update bills
        List<PaymentAllocation> allocations = paymentAllocationRepository.findByPaymentId(id);

        // Delete allocations
        paymentAllocationRepository.deleteByPaymentId(id);

        // Update bills
        for (PaymentAllocation allocation : allocations) {
            UUID billId = allocation.getBill() != null ? allocation.getBill().getId() : null;
            if (billId != null) {
                updateBillPaidFlag(billId);
            }
        }

        // Delete payment
        paymentRepository.delete(payment);
        createAuditLog("Payment", id, "DELETE");
    }

    /**
     * Update bill paid_flag based on total allocations
     */
    private void updateBillPaidFlag(UUID billId) {
        UtilityBill bill = utilityBillRepository.findById(billId)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));

        BigDecimal allocatedAmount = paymentAllocationRepository.sumAllocatedAmountByBillId(billId);
        
        // Mark as paid if fully allocated - use totalAmount instead of getTotal()
        bill.setPaidFlag(allocatedAmount.compareTo(bill.getTotalAmount()) >= 0);
        utilityBillRepository.save(bill);
    }

    private void createAuditLog(String entityType, UUID entityId, String action) {
        AuditLog auditLog = AuditLog.builder()
                .entityType(entityType)
                .entityId(entityId)
                .action(action)
                .changedBy(authorizationHelper.getCurrentUserId())
                .changedAt(OffsetDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
    }
}
