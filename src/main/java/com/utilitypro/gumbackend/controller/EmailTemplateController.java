package com.utilitypro.gumbackend.controller;

import com.utilitypro.gumbackend.dto.email.*;
import com.utilitypro.gumbackend.service.EmailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

/**
 * REST Controller for Email Template Management
 * Handles email template CRUD operations and provider settings
 */
@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    /**
     * GET /api/email/templates
     * List email templates
     */
    @GetMapping("/templates")
    @PreAuthorize("hasRole('system_admin')")
    public ResponseEntity<com.utilitypro.gumbackend.dto.common.PageResponse<EmailTemplateResponse>> listEmailTemplates(
            org.springframework.data.domain.Pageable pageable) {
        var response = emailTemplateService.listTemplates(pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/email/templates
     * Create email template
     */
    @PostMapping("/templates")
    @PreAuthorize("hasRole('system_admin')")
    public ResponseEntity<EmailTemplateResponse> createEmailTemplate(@Valid @RequestBody CreateEmailTemplateRequest request) {
        EmailTemplateResponse response = emailTemplateService.createTemplate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * PUT /api/email/templates/:id
     * Update email template
     */
    @PutMapping("/templates/{id}")
    @PreAuthorize("hasRole('system_admin')")
    public ResponseEntity<EmailTemplateResponse> updateEmailTemplate(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateEmailTemplateRequest request) {
        EmailTemplateResponse response = emailTemplateService.updateTemplate(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/email/templates/:id
     * Delete email template
     */
    @DeleteMapping("/templates/{id}")
    @PreAuthorize("hasRole('system_admin')")
    public ResponseEntity<Void> deleteEmailTemplate(@PathVariable UUID id) {
        emailTemplateService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * PUT /api/email/providers/resend-key
     * Update Resend API key for email provider
     */
    @PutMapping("/providers/resend-key")
    @PreAuthorize("hasRole('system_admin')")
    public ResponseEntity<Void> updateResendKey(@Valid @RequestBody UpdateResendKeyRequest request) {
        emailTemplateService.updateResendApiKey(request.apiKey());
        return ResponseEntity.noContent().build();
    }
}
