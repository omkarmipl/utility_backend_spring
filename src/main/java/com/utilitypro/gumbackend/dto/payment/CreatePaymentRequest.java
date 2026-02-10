package com.utilitypro.gumbackend.dto.payment;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record CreatePaymentRequest(
        @NotNull String paymentReference,
        @NotNull java.util.UUID billId,
        @NotNull BigDecimal amount,
        OffsetDateTime paidDate,
        String paymentMethod
) {}
