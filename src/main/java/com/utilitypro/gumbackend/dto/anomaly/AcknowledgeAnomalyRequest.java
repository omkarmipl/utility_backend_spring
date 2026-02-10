package com.utilitypro.gumbackend.dto.anomaly;

import jakarta.validation.constraints.NotNull;

public record AcknowledgeAnomalyRequest(@NotNull java.util.UUID anomalyId, String comments) {}
