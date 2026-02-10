package com.utilitypro.gumbackend.dto.payment;

public record PaymentResponse(
        java.util.UUID id,
        String paymentReference,
        java.math.BigDecimal amount,
        String status
) {}
