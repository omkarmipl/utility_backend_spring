package com.utilitypro.gumbackend.dto.anomaly;

import java.math.BigDecimal;

public record UpdateThresholdRequest(BigDecimal thresholdValue, Boolean isActive) {}
