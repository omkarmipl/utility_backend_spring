package com.utilitypro.gumbackend.dto.job;

import java.time.OffsetDateTime;
import java.util.List;

public record NotificationHistoryResponse(List<NotificationItem> notifications) {
    public record NotificationItem(
            java.util.UUID id,
            String type,
            String status,
            OffsetDateTime sentAt
    ) {}
}
