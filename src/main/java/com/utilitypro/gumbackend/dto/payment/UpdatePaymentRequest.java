package com.utilitypro.gumbackend.dto.payment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record UpdatePaymentRequest(
        BigDecimal amount,
        String status,
        OffsetDateTime paidDate
) {}
