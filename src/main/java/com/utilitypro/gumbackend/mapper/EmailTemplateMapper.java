package com.utilitypro.gumbackend.mapper;

import com.utilitypro.gumbackend.domain.entity.EmailTemplate;
import com.utilitypro.gumbackend.dto.email.CreateEmailTemplateRequest;
import com.utilitypro.gumbackend.dto.email.EmailTemplateResponse;
import com.utilitypro.gumbackend.dto.email.UpdateEmailTemplateRequest;
import org.springframework.stereotype.Component;

/**
 * Mapper for EmailTemplate entities and DTOs
 */
@Component
public class EmailTemplateMapper {

    public EmailTemplateResponse toResponse(EmailTemplate entity) {
        return EmailTemplateResponse.builder()
                .id(entity.getId())
                .templateName(entity.getTemplateName())
                .subject(entity.getSubject())
                .body(entity.getBody())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public EmailTemplate toEntity(CreateEmailTemplateRequest request) {
        return EmailTemplate.builder()
                .templateName(request.templateName())
                .subject(request.subject())
                .body(request.content()) // DTO has content, Entity has body
                .isActive(true) // Default active
                .build();
    }

    public void updateEntityFromRequest(EmailTemplate entity, UpdateEmailTemplateRequest request) {
        // templateName is not in UpdateEmailTemplateRequest
        if (request.subject() != null) entity.setSubject(request.subject());
        if (request.content() != null) entity.setBody(request.content()); // DTO has content
        if (request.isActive() != null) entity.setIsActive(request.isActive());
    }
}
