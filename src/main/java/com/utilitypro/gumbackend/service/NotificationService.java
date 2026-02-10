package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.InAppNotification;
import com.utilitypro.gumbackend.repository.InAppNotificationRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Notification Service
 * Handles in-app notifications for users
 */
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final InAppNotificationRepository inAppNotificationRepository;
    private final AuthorizationHelper authorizationHelper;

    @Transactional(readOnly = true)
    public List<InAppNotification> listNotifications(Integer limit) {
        UUID userId = authorizationHelper.getCurrentUserId();
        List<InAppNotification> notifications = inAppNotificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        
        if (limit != null && limit > 0 && notifications.size() > limit) {
            return notifications.subList(0, limit);
        }
        return notifications;
    }

    @Transactional(readOnly = true)
    public long getUnreadCount() {
        UUID userId = authorizationHelper.getCurrentUserId();
        return inAppNotificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    @Transactional
    public void markAsRead(UUID notificationId) {
        InAppNotification notification = inAppNotificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

        // Check ownership
        authorizationHelper.checkOwnership(notification.getUserId());

        notification.setIsRead(true);
        inAppNotificationRepository.save(notification);
    }

    @Transactional
    public void markAllAsRead() {
        UUID userId = authorizationHelper.getCurrentUserId();
        List<InAppNotification> notifications = inAppNotificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        
        for (InAppNotification notification : notifications) {
            notification.setIsRead(true);
            inAppNotificationRepository.save(notification);
        }
    }

    @Transactional
    public void deleteNotification(UUID notificationId) {
        InAppNotification notification = inAppNotificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

        // Check ownership
        authorizationHelper.checkOwnership(notification.getUserId());

        inAppNotificationRepository.delete(notification);
    }
}
