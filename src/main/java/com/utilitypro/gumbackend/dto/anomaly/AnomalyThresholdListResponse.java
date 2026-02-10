package com.utilitypro.gumbackend.dto.anomaly;

import java.util.List;

public record AnomalyThresholdListResponse(List<ThresholdItem> thresholds) {
    public record ThresholdItem(java.util.UUID id, String name, String ruleType, Double value, Boolean isActive) {}
}
