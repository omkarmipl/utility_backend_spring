package com.utilitypro.gumbackend.mapper;

import com.utilitypro.gumbackend.domain.entity.*;
import com.utilitypro.gumbackend.dto.bill.*;
import org.springframework.stereotype.Component;

/**
 * Mapper for Bill entities and DTOs
 */
@Component
public class BillMapper {

    public BillResponse toResponse(UtilityBill entity) {
        return new BillResponse(
                entity.getId(),
                entity.getInvoiceNumber(),
                entity.getInvoiceDate(),
                entity.getBillingPeriodFrom(),
                entity.getBillingPeriodTo(),
                entity.getDueDate(),
                entity.getSubtotalAmount(),
                entity.getTaxAmount(),
                entity.getOtherCharges(),
                entity.getTotalAmount(),
                entity.getCurrencyCode(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getPaidFlag(),
                entity.getScannedInvoiceUrl(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getAccount() != null ? entity.getAccount().getId() : null,
                entity.getMinistry() != null ? entity.getMinistry().getId() : null,
                entity.getDepartment() != null ? entity.getDepartment().getId() : null
        );
    }

    public UtilityBill toEntity(CreateBillRequest request) {
        UtilityBill bill = new UtilityBill();
        // Assuming account lookup happens in service or we just set ID if entity has ID field (it doesn't, it has Objects)
        // bill.setAccount(...); // Service needs to handle fetching Account, Ministry, Department
        
        bill.setInvoiceNumber(request.billNumber()); // Mapping billNumber to invoiceNumber
        bill.setDueDate(request.dueDate() != null ? request.dueDate().toLocalDate() : null); 
        // Note: Request has OffsetDateTime, Entity has LocalDate for dates. 
        // We should probably update Request to use LocalDate for these fields or convert.
        // Given previous step updated request to have LocalDate for billing periods:
        bill.setBillingPeriodFrom(request.billingPeriodFrom());
        bill.setBillingPeriodTo(request.billingPeriodTo());
        
        bill.setSubtotalAmount(request.amount()); // Mapping amount to subtotal check?
        // Actually CreateBillRequest has amount, taxAmount, otherCharges.
        bill.setTaxAmount(request.taxAmount());
        bill.setOtherCharges(request.otherCharges());
        
        // billDate in request -> invoiceDate in entity
        bill.setInvoiceDate(request.billDate() != null ? request.billDate().toLocalDate() : java.time.LocalDate.now());

        return bill;
    }

    public void updateEntityFromRequest(UtilityBill entity, UpdateBillRequest request) {
        if (request.amount() != null) entity.setSubtotalAmount(request.amount());
        if (request.taxAmount() != null) entity.setTaxAmount(request.taxAmount());
        if (request.otherCharges() != null) entity.setOtherCharges(request.otherCharges());
        if (request.billingPeriodFrom() != null) entity.setBillingPeriodFrom(request.billingPeriodFrom());
        if (request.billingPeriodTo() != null) entity.setBillingPeriodTo(request.billingPeriodTo());
        if (request.dueDate() != null) entity.setDueDate(request.dueDate().toLocalDate());
        
        // Status update is usually separate but if included:
        if (request.status() != null) {
            try {
                entity.setStatus(com.utilitypro.gumbackend.domain.enums.BillStatus.valueOf(request.status().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Ignore or handle
            }
        }
    }
    public BillListResponse.BillItem toBillListItem(UtilityBill entity) {
        return new BillListResponse.BillItem(
                entity.getId(),
                entity.getInvoiceNumber(),
                entity.getTotalAmount(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getDueDate() != null ? java.time.OffsetDateTime.of(entity.getDueDate(), java.time.LocalTime.MIDNIGHT, java.time.ZoneOffset.UTC) : null 
        );
    }

    public BillDetailResponse toBillDetailResponse(UtilityBill entity) {
        return new BillDetailResponse(
                entity.getId(),
                entity.getInvoiceNumber(),
                entity.getTotalAmount(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getDueDate() != null ? java.time.OffsetDateTime.of(entity.getDueDate(), java.time.LocalTime.MIDNIGHT, java.time.ZoneOffset.UTC) : null,
                entity.getInvoiceDate() != null ? java.time.OffsetDateTime.of(entity.getInvoiceDate(), java.time.LocalTime.MIDNIGHT, java.time.ZoneOffset.UTC) : null,
                entity.getAccount() != null ? entity.getAccount().getId() : null,
                entity.getAccount() != null ? entity.getAccount().getProviderAccountNo() : null
        );
    }

    public BillStatusResponse toBillStatusResponse(UtilityBill entity) {
        return new BillStatusResponse(
                entity.getId(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getUpdatedAt()
        );
    }

    public UnpaidBillListResponse.BillItem toUnpaidBillItem(UtilityBill entity) {
        return new UnpaidBillListResponse.BillItem(
                entity.getId(),
                entity.getTotalAmount(),
                entity.getDueDate() != null ? java.time.OffsetDateTime.of(entity.getDueDate(), java.time.LocalTime.MIDNIGHT, java.time.ZoneOffset.UTC) : null
        );
    }
    public BillUsageLine toBillUsageLine(CreateBillUsageLineRequest dto) {
        BillUsageLine entity = new BillUsageLine();
        entity.setMeterIdentifier(dto.meterIdentifier());
        entity.setTariffDescription(dto.tariffDescription());
        entity.setPrevReading(dto.prevReading());
        entity.setCurrReading(dto.currReading());
        entity.setUsageQuantity(dto.usageQuantity());
        entity.setRatePerUnit(dto.ratePerUnit());
        entity.setLineAmount(dto.lineAmount());
        return entity;
    }
}
