package com.utilitypro.gumbackend.dto.email;

import lombok.Builder;
import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record EmailTemplateResponse(
        UUID id,
        String templateName,
        String subject,
        String body,
        Boolean isActive,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
