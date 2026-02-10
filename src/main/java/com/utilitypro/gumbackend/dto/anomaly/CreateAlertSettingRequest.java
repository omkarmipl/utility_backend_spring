package com.utilitypro.gumbackend.dto.anomaly;

import jakarta.validation.constraints.NotNull;

public record CreateAlertSettingRequest(@NotNull String alertType, Boolean isEnabled) {}
