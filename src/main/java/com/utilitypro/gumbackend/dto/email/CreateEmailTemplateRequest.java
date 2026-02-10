package com.utilitypro.gumbackend.dto.email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateEmailTemplateRequest(
        @NotBlank String templateName,
        @NotBlank String subject,
        @NotBlank String content,
        String providerTemplateId
) {}
