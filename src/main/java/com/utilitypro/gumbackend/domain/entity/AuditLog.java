package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
@NoArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "entity_type", nullable = false, columnDefinition = "text")
    private String entityType;

    @Column(name = "entity_id", nullable = false)
    private UUID entityId;

    @Column(nullable = false, columnDefinition = "text")
    private String action;

    @Column(name = "old_values", columnDefinition = "json")
    private String oldValues;

    @Column(name = "new_values", columnDefinition = "json")
    private String newValues;

    @Column(name = "changed_by")
    private UUID changedBy;

    @Column(name = "ip_address", columnDefinition = "text")
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "text")
    private String userAgent;

    @Column(name = "changed_at", nullable = false)
    private OffsetDateTime changedAt;

    public AuditLog(UUID id, String entityType, UUID entityId, String action, String oldValues, String newValues, UUID changedBy, String ipAddress, String userAgent, OffsetDateTime changedAt) {
        this.id = id;
        this.entityType = entityType;
        this.entityId = entityId;
        this.action = action;
        this.oldValues = oldValues;
        this.newValues = newValues;
        this.changedBy = changedBy;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.changedAt = changedAt;
    }

    public static AuditLogBuilder builder() {
        return new AuditLogBuilder();
    }

    public static class AuditLogBuilder {
        private UUID id;
        private String entityType;
        private UUID entityId;
        private String action;
        private String oldValues;
        private String newValues;
        private UUID changedBy;
        private String ipAddress;
        private String userAgent;
        private OffsetDateTime changedAt;

        AuditLogBuilder() {}

        public AuditLogBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public AuditLogBuilder entityType(String entityType) {
            this.entityType = entityType;
            return this;
        }

        public AuditLogBuilder entityId(UUID entityId) {
            this.entityId = entityId;
            return this;
        }

        public AuditLogBuilder action(String action) {
            this.action = action;
            return this;
        }

        public AuditLogBuilder oldValues(String oldValues) {
            this.oldValues = oldValues;
            return this;
        }

        public AuditLogBuilder newValues(String newValues) {
            this.newValues = newValues;
            return this;
        }

        public AuditLogBuilder changedBy(UUID changedBy) {
            this.changedBy = changedBy;
            return this;
        }

        public AuditLogBuilder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public AuditLogBuilder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public AuditLogBuilder changedAt(OffsetDateTime changedAt) {
            this.changedAt = changedAt;
            return this;
        }

        public AuditLog build() {
            return new AuditLog(id, entityType, entityId, action, oldValues, newValues, changedBy, ipAddress, userAgent, changedAt);
        }
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public UUID getEntityId() { return entityId; }
    public void setEntityId(UUID entityId) { this.entityId = entityId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getOldValues() { return oldValues; }
    public void setOldValues(String oldValues) { this.oldValues = oldValues; }

    public String getNewValues() { return newValues; }
    public void setNewValues(String newValues) { this.newValues = newValues; }

    public UUID getChangedBy() { return changedBy; }
    public void setChangedBy(UUID changedBy) { this.changedBy = changedBy; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public OffsetDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(OffsetDateTime changedAt) { this.changedAt = changedAt; }
}
