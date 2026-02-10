package com.utilitypro.gumbackend.domain.entity;

import com.utilitypro.gumbackend.domain.enums.GumRole;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_roles", uniqueConstraints = @UniqueConstraint(name = "uk_user_roles_user_role", columnNames = {"user_id", "role"}))
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GumRole role;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }

    public UserRole(UUID id, UUID userId, GumRole role, OffsetDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.role = role;
        this.createdAt = createdAt;
    }

    public static UserRoleBuilder builder() {
        return new UserRoleBuilder();
    }

    public static class UserRoleBuilder {
        private UUID id;
        private UUID userId;
        private GumRole role;
        private OffsetDateTime createdAt;

        UserRoleBuilder() {}

        public UserRoleBuilder id(UUID id) { this.id = id; return this; }
        public UserRoleBuilder userId(UUID userId) { this.userId = userId; return this; }
        public UserRoleBuilder role(GumRole role) { this.role = role; return this; }
        public UserRoleBuilder roleName(String roleName) {
            try {
                this.role = GumRole.valueOf(roleName);
            } catch (IllegalArgumentException e) {
                // handle or ignore, maybe defaults?
                // For now, let's assume it matches or leave null if not
            }
            return this;
        }
        public UserRoleBuilder createdAt(OffsetDateTime createdAt) { this.createdAt = createdAt; return this; }

        public UserRole build() {
            return new UserRole(id, userId, role, createdAt);
        }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public GumRole getRole() { return role; }
    public void setRole(GumRole role) { this.role = role; }

    public String getRoleName() {
        return role != null ? role.name() : null;
    }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
