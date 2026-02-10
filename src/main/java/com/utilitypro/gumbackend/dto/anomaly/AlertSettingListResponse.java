package com.utilitypro.gumbackend.dto.anomaly;

import java.util.List;

public record AlertSettingListResponse(List<AlertSettingItem> settings) {
    public record AlertSettingItem(
            java.util.UUID id,
            String alertType,
            Boolean isEnabled
    ) {}
}
