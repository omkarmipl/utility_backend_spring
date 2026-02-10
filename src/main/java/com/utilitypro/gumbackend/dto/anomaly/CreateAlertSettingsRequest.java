package com.utilitypro.gumbackend.dto.anomaly;

import jakarta.validation.constraints.NotNull;

public record CreateAlertSettingsRequest(@NotNull String alertType, @NotNull String recipient) {}
