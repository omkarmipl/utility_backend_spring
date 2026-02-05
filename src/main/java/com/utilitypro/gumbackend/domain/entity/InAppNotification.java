package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "in_app_notifications")
@Getter
@Setter
@NoArgsConstructor
public class InAppNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false, columnDefinition = "text")
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String message;

    @Column(nullable = false, columnDefinition = "text")
    private String type;

    @Column(name = "entity_type", columnDefinition = "text")
    private String entityType;

    @Column(name = "entity_id")
    private UUID entityId;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
