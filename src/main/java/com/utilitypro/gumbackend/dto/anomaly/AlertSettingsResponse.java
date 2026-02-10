package com.utilitypro.gumbackend.dto.anomaly;

public record AlertSettingsResponse(java.util.UUID id, String alertType, String recipient, Boolean enabled) {}
