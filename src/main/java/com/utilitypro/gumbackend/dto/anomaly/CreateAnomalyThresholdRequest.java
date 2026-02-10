package com.utilitypro.gumbackend.dto.anomaly;

import jakarta.validation.constraints.NotNull;

public record CreateAnomalyThresholdRequest(@NotNull String name, @NotNull String ruleType, @NotNull Double value) {}
