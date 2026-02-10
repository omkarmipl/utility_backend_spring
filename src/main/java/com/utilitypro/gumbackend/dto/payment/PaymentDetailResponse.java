package com.utilitypro.gumbackend.dto.payment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PaymentDetailResponse(
        java.util.UUID id,
        String paymentReference,
        BigDecimal amount,
        String status,
        OffsetDateTime paidDate,
        java.util.UUID billId,
        String billNumber
) {}
