package com.utilitypro.gumbackend.dto.payment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record PaymentListResponse(List<PaymentItem> payments) {
    public record PaymentItem(
            java.util.UUID id,
            String paymentReference,
            BigDecimal amount,
            String status,
            OffsetDateTime paidDate
    ) {}
}
