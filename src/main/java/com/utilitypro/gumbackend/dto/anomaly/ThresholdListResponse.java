package com.utilitypro.gumbackend.dto.anomaly;

import java.util.List;

public record ThresholdListResponse(List<ThresholdItem> thresholds) {
    public record ThresholdItem(
            java.util.UUID id,
            String metric,
            java.math.BigDecimal thresholdValue,
            Boolean isActive
    ) {}
}
