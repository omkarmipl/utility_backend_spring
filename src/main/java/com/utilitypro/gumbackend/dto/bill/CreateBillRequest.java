package com.utilitypro.gumbackend.dto.bill;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record CreateBillRequest(
        @NotNull String billNumber,
        @NotNull java.util.UUID accountId,
        @NotNull BigDecimal amount, // This maps to subtotalAmount? Or total? 
        @NotNull java.time.LocalDate billingPeriodFrom,
        @NotNull java.time.LocalDate billingPeriodTo,
        BigDecimal taxAmount,
        BigDecimal otherCharges,
        OffsetDateTime billDate, // invoiceDate?
        OffsetDateTime dueDate,
        java.util.List<CreateBillUsageLineRequest> usageLines
) {}
