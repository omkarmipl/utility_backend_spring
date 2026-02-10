package com.utilitypro.gumbackend.dto.anomaly;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateThresholdRequest(
        @NotNull String metric,
        @NotNull BigDecimal thresholdValue,
        Boolean isActive
) {}
