package com.utilitypro.gumbackend.mapper;

import com.utilitypro.gumbackend.domain.entity.InAppNotification;
import com.utilitypro.gumbackend.dto.notification.NotificationResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for Notification entities and DTOs
 */
@Component
public class NotificationMapper {

    public NotificationResponse toResponse(InAppNotification entity) {
        return NotificationResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .message(entity.getMessage())
                .type(entity.getType())
                .isRead(entity.getIsRead())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toLocalDateTime() : null)
                .build();
    }
}
