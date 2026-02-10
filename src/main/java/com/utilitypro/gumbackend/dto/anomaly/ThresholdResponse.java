package com.utilitypro.gumbackend.dto.anomaly;

public record ThresholdResponse(
        java.util.UUID id,
        String metric,
        java.math.BigDecimal thresholdValue,
        Boolean isActive
) {}
