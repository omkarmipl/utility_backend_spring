package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.domain.entity.BillUsageLine;
import com.utilitypro.gumbackend.domain.entity.UtilityBill;
import com.utilitypro.gumbackend.domain.enums.BillStatus;
import com.utilitypro.gumbackend.dto.bill.*;
import com.utilitypro.gumbackend.mapper.BillMapper;
import com.utilitypro.gumbackend.repository.AuditLogRepository;
import com.utilitypro.gumbackend.repository.BillUsageLineRepository;
import com.utilitypro.gumbackend.repository.PaymentAllocationRepository;
import com.utilitypro.gumbackend.repository.UtilityBillRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Bill Service
 * Handles bill CRUD, status updates, and unpaid bill queries
 */
@Service
@RequiredArgsConstructor
public class BillService {

    private final UtilityBillRepository utilityBillRepository;
    private final BillUsageLineRepository billUsageLineRepository;
    private final PaymentAllocationRepository paymentAllocationRepository;
    private final AuditLogRepository auditLogRepository;
    private final AuthorizationHelper authorizationHelper;
    private final BillMapper billMapper;

    @Transactional(readOnly = true)
    public BillListResponse listBills(UUID ministryId, String status, Integer page, Integer limit) {
        PageRequest pageRequest = PageRequest.of(page != null ? page : 0, limit != null ? limit : 10);
        BillStatus statusEnum = null;
        if (status != null && !status.isEmpty()) {
            try {
                statusEnum = BillStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Ignore or handle
            }
        }
        Page<UtilityBill> billsPage = utilityBillRepository.findWithFilters(ministryId, statusEnum, pageRequest);
        
        List<BillListResponse.BillItem> items = billsPage.getContent().stream()
                .map(billMapper::toBillListItem)
                .toList();

        return new BillListResponse(items, (int) billsPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public BillDetailResponse getBill(UUID id) {
        UtilityBill bill = utilityBillRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));
        return billMapper.toBillDetailResponse(bill);
    }

    @Transactional
    public BillResponse createBill(CreateBillRequest request) {
        authorizationHelper.requireRole("system_admin", "mof_admin", "ministry_admin", "department_user");

        UtilityBill bill = billMapper.toEntity(request);
        
        BigDecimal calculatedTotal = bill.getSubtotalAmount()
                .add(bill.getTaxAmount() != null ? bill.getTaxAmount() : BigDecimal.ZERO)
                .add(bill.getOtherCharges() != null ? bill.getOtherCharges() : BigDecimal.ZERO);
        
        bill.setTotalAmount(calculatedTotal);
        bill.setStatus(BillStatus.DRAFT);
        bill.setPaidFlag(false);
        bill.setCreatedBy(authorizationHelper.getCurrentUserId()); // method might mean something else? "system"?
        // auditLog uses authorizationHelper.getCurrentUserId() which returns String/UUID?
        // auditLog.changedBy is String/UUID?
        // UtilityBill doesn't have createdBy/updatedBy fields in the entity view in Step 928?
        // Step 928 shows `createdAt`, `updatedAt`. No `createdBy`.
        // So I'll skip setCreatedBy.

        bill.setCreatedAt(OffsetDateTime.now());
        bill.setUpdatedAt(OffsetDateTime.now());

        UtilityBill saved = utilityBillRepository.save(bill);

        if (request.usageLines() != null && !request.usageLines().isEmpty()) {
            for (CreateBillUsageLineRequest lineDto : request.usageLines()) {
                BillUsageLine line = billMapper.toBillUsageLine(lineDto);
                line.setBill(saved);
                billUsageLineRepository.save(line);
            }
        }

        createAuditLog("UtilityBill", saved.getId(), "CREATE");

        return billMapper.toResponse(saved);
    }

    @Transactional
    public BillResponse updateBill(UUID id, UpdateBillRequest request) {
        authorizationHelper.requireRole("system_admin", "mof_admin", "ministry_admin", "department_user");

        UtilityBill existing = utilityBillRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));

        billMapper.updateEntityFromRequest(existing, request);
        
        BigDecimal calculatedTotal = existing.getSubtotalAmount()
                .add(existing.getTaxAmount() != null ? existing.getTaxAmount() : BigDecimal.ZERO)
                .add(existing.getOtherCharges() != null ? existing.getOtherCharges() : BigDecimal.ZERO);
        existing.setTotalAmount(calculatedTotal);
        existing.setUpdatedAt(OffsetDateTime.now());

        UtilityBill saved = utilityBillRepository.save(existing);
        createAuditLog("UtilityBill", saved.getId(), "UPDATE");
        return billMapper.toResponse(saved);
    }

    @Transactional
    public BillStatusResponse updateBillStatus(UUID id, com.utilitypro.gumbackend.dto.bill.UpdateBillStatusRequest request) {
        authorizationHelper.requireRole("system_admin", "mof_admin", "ministry_admin");

        UtilityBill bill = utilityBillRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));

        BillStatus newStatus;
        try {
            newStatus = BillStatus.valueOf(request.status().toUpperCase());
        } catch (IllegalArgumentException e) {
             throw new IllegalArgumentException("Invalid status: " + request.status());
        }

        validateStatusTransition(bill.getStatus(), newStatus);

        bill.setStatus(newStatus);
        bill.setUpdatedAt(OffsetDateTime.now());
        UtilityBill saved = utilityBillRepository.save(bill);

        createAuditLog("UtilityBill", saved.getId(), "STATUS_UPDATE");

        return billMapper.toBillStatusResponse(saved);
    }

    @Transactional(readOnly = true)
    public UnpaidBillListResponse listUnpaidBills(UUID providerId) {
        List<UtilityBill> unpaidBills = utilityBillRepository.findUnpaidBills();
        
        BigDecimal totalUnpaid = BigDecimal.ZERO;
        List<UnpaidBillListResponse.BillItem> items = new java.util.ArrayList<>();
        
        for (UtilityBill bill : unpaidBills) {
            BigDecimal allocatedAmount = paymentAllocationRepository.sumAllocatedAmountByBillId(bill.getId());
            if (allocatedAmount == null) allocatedAmount = BigDecimal.ZERO;
            
            BigDecimal remaining = bill.getTotalAmount().subtract(allocatedAmount);
            if (remaining.compareTo(BigDecimal.ZERO) > 0) {
                 totalUnpaid = totalUnpaid.add(remaining);
                 items.add(billMapper.toUnpaidBillItem(bill));
            }
        }

        return new UnpaidBillListResponse(items, totalUnpaid);
    }

    private void validateStatusTransition(BillStatus currentStatus, BillStatus newStatus) {
        if (BillStatus.PAID.equals(currentStatus)) {
            throw new IllegalArgumentException("Cannot change status of paid bill");
        }
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
