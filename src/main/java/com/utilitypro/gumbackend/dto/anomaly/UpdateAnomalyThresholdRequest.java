package com.utilitypro.gumbackend.dto.anomaly;

public record UpdateAnomalyThresholdRequest(String name, Double value, Boolean isActive) {}
