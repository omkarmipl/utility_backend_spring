package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification_history")
@Getter
@Setter
@NoArgsConstructor
public class NotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "notification_type", nullable = false, columnDefinition = "text")
    private String notificationType;

    @Column(nullable = false, columnDefinition = "text")
    private String subject;

    @Column(nullable = false, columnDefinition = "json")
    private String recipients;

    @Column(name = "anomaly_count", nullable = false)
    private Integer anomalyCount;

    @Column(nullable = false, columnDefinition = "text")
    private String status;

    @Column(name = "error_message", columnDefinition = "text")
    private String errorMessage;

    @Column(columnDefinition = "json")
    private String metadata;

    @Column(name = "bounce_count")
    private Integer bounceCount;

    @Column(name = "bounced_emails", columnDefinition = "json")
    private String bouncedEmails;

    @Column(name = "sent_at", nullable = false)
    private OffsetDateTime sentAt;
}
