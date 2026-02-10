package com.utilitypro.gumbackend.dto.notification;
import java.util.List;
public record NotificationListResponse(List<NotificationItem> notifications) {
    public record NotificationItem(java.util.UUID id, String message, String type, Boolean isRead, java.time.OffsetDateTime createdAt) {}
}
