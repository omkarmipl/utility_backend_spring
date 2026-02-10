package com.utilitypro.gumbackend.dto.bill;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record UpdateBillRequest(
        BigDecimal amount,
        String status,
        OffsetDateTime dueDate,
        java.time.LocalDate billingPeriodFrom,
        java.time.LocalDate billingPeriodTo,
        BigDecimal taxAmount,
        BigDecimal otherCharges
) {}
