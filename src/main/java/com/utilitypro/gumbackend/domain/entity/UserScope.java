package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_scopes")
@NoArgsConstructor
public class UserScope {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ministry_id")
    private Ministry ministry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }

    public UserScope(UUID id, UUID userId, Ministry ministry, Department department, OffsetDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.ministry = ministry;
        this.department = department;
        this.createdAt = createdAt;
    }

    public static UserScopeBuilder builder() {
        return new UserScopeBuilder();
    }

    public static class UserScopeBuilder {
        private UUID id;
        private UUID userId;
        private Ministry ministry;
        private Department department;
        private OffsetDateTime createdAt;

        UserScopeBuilder() {}

        public UserScopeBuilder id(UUID id) { this.id = id; return this; }
        public UserScopeBuilder userId(UUID userId) { this.userId = userId; return this; }
        public UserScopeBuilder ministry(Ministry ministry) { this.ministry = ministry; return this; }
        public UserScopeBuilder department(Department department) { this.department = department; return this; }
        public UserScopeBuilder createdAt(OffsetDateTime createdAt) { this.createdAt = createdAt; return this; }

        public UserScope build() {
            return new UserScope(id, userId, ministry, department, createdAt);
        }
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public Ministry getMinistry() { return ministry; }
    public void setMinistry(Ministry ministry) { this.ministry = ministry; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
