package com.utilitypro.gumbackend.dto.anomaly;

public record AnomalyThresholdResponse(java.util.UUID id, String name, String ruleType, Double value, Boolean isActive) {}
