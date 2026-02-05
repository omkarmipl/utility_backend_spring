package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "alert_settings")
@Getter
@Setter
@NoArgsConstructor
public class AlertSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false, columnDefinition = "text")
    private String frequency;

    @Column(name = "min_severity", nullable = false, columnDefinition = "text")
    private String minSeverity;

    @Column(name = "sensitivity_level", nullable = false, columnDefinition = "text")
    private String sensitivityLevel;

    @Column(name = "include_drops", nullable = false)
    private Boolean includeDrops;

    @Column(nullable = false, columnDefinition = "json")
    private String recipients;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
