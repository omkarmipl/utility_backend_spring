package com.utilitypro.gumbackend.dto.settings;
import jakarta.validation.constraints.NotNull;
public record UpdateSystemSettingsRequest(@NotNull String settingKey, @NotNull String settingValue) {}
