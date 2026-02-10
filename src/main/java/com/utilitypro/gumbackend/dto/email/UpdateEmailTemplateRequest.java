package com.utilitypro.gumbackend.dto.email;

public record UpdateEmailTemplateRequest(
        String subject,
        String content,
        String providerTemplateId,
        Boolean isActive
) {}
