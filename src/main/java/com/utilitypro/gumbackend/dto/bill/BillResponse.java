package com.utilitypro.gumbackend.dto.bill;

public record BillResponse(
        java.util.UUID id,
        String invoiceNumber,
        java.time.LocalDate invoiceDate,
        java.time.LocalDate billingPeriodFrom,
        java.time.LocalDate billingPeriodTo,
        java.time.LocalDate dueDate,
        java.math.BigDecimal subtotalAmount,
        java.math.BigDecimal taxAmount,
        java.math.BigDecimal otherCharges,
        java.math.BigDecimal totalAmount,
        String currencyCode,
        String status,
        Boolean paidFlag,
        String scannedInvoiceUrl,
        String notes,
        java.time.OffsetDateTime createdAt,
        java.time.OffsetDateTime updatedAt,
        java.util.UUID accountId,
        java.util.UUID ministryId,
        java.util.UUID departmentId
) {}
