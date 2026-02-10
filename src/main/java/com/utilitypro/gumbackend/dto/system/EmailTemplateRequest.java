package com.utilitypro.gumbackend.dto.system;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Email template request DTO
 */
public record EmailTemplateRequest(
        
        @NotBlank(message = "Template name is required")
        @Size(max = 100, message = "Template name must be less than 100 characters")
        String templateName,
        
        @NotBlank(message = "Subject is required")
        @Size(max = 255, message = "Subject must be less than 255 characters")
        String subject,
        
        @NotBlank(message = "Body is required")
        String body
) {}
