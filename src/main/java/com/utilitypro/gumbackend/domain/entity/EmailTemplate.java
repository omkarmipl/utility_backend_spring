package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_templates")
@NoArgsConstructor
public class EmailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "template_name", unique = true, nullable = false)
    private String templateName;

    @Column(nullable = false, columnDefinition = "text")
    private String subject;

    @Column(nullable = false, columnDefinition = "text")
    private String body;

    @Column(name = "header_text", columnDefinition = "text")
    private String headerText;

    @Column(name = "footer_text", columnDefinition = "text")
    private String footerText;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public EmailTemplate(UUID id, String templateName, String subject, String body, String headerText, String footerText, Boolean isActive, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.templateName = templateName;
        this.subject = subject;
        this.body = body;
        this.headerText = headerText;
        this.footerText = footerText;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static EmailTemplateBuilder builder() {
        return new EmailTemplateBuilder();
    }

    public static class EmailTemplateBuilder {
        private UUID id;
        private String templateName;
        private String subject;
        private String body;
        private String headerText;
        private String footerText;
        private Boolean isActive;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        EmailTemplateBuilder() {}

        public EmailTemplateBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public EmailTemplateBuilder templateName(String templateName) {
            this.templateName = templateName;
            return this;
        }

        public EmailTemplateBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailTemplateBuilder body(String body) {
            this.body = body;
            return this;
        }

        public EmailTemplateBuilder headerText(String headerText) {
            this.headerText = headerText;
            return this;
        }

        public EmailTemplateBuilder footerText(String footerText) {
            this.footerText = footerText;
            return this;
        }

        public EmailTemplateBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public EmailTemplateBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public EmailTemplateBuilder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public EmailTemplate build() {
            return new EmailTemplate(id, templateName, subject, body, headerText, footerText, isActive, createdAt, updatedAt);
        }
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getHeaderText() { return headerText; }
    public void setHeaderText(String headerText) { this.headerText = headerText; }

    public String getFooterText() { return footerText; }
    public void setFooterText(String footerText) { this.footerText = footerText; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
