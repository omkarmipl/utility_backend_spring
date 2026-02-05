package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_bounces")
@Getter
@Setter
@NoArgsConstructor
public class EmailBounce {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private NotificationHistory notification;

    @Column(nullable = false, columnDefinition = "text")
    private String email;

    @Column(name = "bounce_type", nullable = false, columnDefinition = "text")
    private String bounceType;

    @Column(name = "bounce_reason", columnDefinition = "text")
    private String bounceReason;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "bounced_at", nullable = false)
    private OffsetDateTime bouncedAt;
}
