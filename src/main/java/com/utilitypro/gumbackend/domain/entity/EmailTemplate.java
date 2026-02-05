package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_templates")
@Getter
@Setter
@NoArgsConstructor
public class EmailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "template_key", unique = true)
    private String templateKey;

    @Column(nullable = false, columnDefinition = "text")
    private String subject;

    @Column(name = "header_text", nullable = false, columnDefinition = "text")
    private String headerText;

    @Column(name = "footer_text", nullable = false, columnDefinition = "text")
    private String footerText;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
