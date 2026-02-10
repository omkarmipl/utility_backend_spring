package com.utilitypro.gumbackend.dto.anomaly;

import java.util.List;

public record AlertSettingsListResponse(List<AlertSettingItem> settings) {
    public record AlertSettingItem(java.util.UUID id, String alertType, String recipient, Boolean enabled) {}
}
