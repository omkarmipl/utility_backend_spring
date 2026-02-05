package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "navigation_items")
@Getter
@Setter
@NoArgsConstructor
public class NavigationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nav_key", unique = true, columnDefinition = "text")
    private String navKey;

    @Column(nullable = false, columnDefinition = "text")
    private String label;

    @Column(nullable = false, columnDefinition = "text")
    private String href;

    @Column(nullable = false, columnDefinition = "text")
    private String icon;

    @Column(nullable = false, columnDefinition = "text")
    private String section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private NavigationItem parent;

    @Column(name = "required_permission", columnDefinition = "text")
    private String requiredPermission;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
