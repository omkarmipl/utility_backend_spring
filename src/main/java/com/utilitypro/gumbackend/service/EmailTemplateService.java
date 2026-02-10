package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.domain.entity.EmailTemplate;
import com.utilitypro.gumbackend.domain.entity.SystemSetting;
import com.utilitypro.gumbackend.dto.common.PageResponse;
import com.utilitypro.gumbackend.dto.email.CreateEmailTemplateRequest;
import com.utilitypro.gumbackend.dto.email.EmailTemplateResponse;
import com.utilitypro.gumbackend.dto.email.EmailTemplateListResponse;
import com.utilitypro.gumbackend.dto.email.UpdateEmailTemplateRequest;
import com.utilitypro.gumbackend.mapper.EmailTemplateMapper;
import com.utilitypro.gumbackend.repository.AuditLogRepository;
import com.utilitypro.gumbackend.repository.EmailTemplateRepository;
import com.utilitypro.gumbackend.repository.SystemSettingRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Email Template Service
 * Handles email template management and Resend API configuration
 */
@Service
@RequiredArgsConstructor
public class EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailTemplateMapper emailTemplateMapper;
    private final SystemSettingRepository systemSettingRepository;
    private final AuditLogRepository auditLogRepository;
    private final AuthorizationHelper authorizationHelper;

    private static final String RESEND_API_KEY_SETTING = "resend_api_key";

    @Transactional(readOnly = true)
    public PageResponse<EmailTemplateResponse> listTemplates(Pageable pageable) {
        authorizationHelper.requireRole("system_admin");
        Page<EmailTemplate> templates = emailTemplateRepository.findAll(pageable);
        return PageResponse.of(templates, emailTemplateMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public EmailTemplateResponse getTemplate(UUID id) {
        authorizationHelper.requireRole("system_admin");
        EmailTemplate template = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Template not found"));
        return emailTemplateMapper.toResponse(template);
    }

    @Transactional
    public EmailTemplateResponse createTemplate(CreateEmailTemplateRequest request) {
        authorizationHelper.requireRole("system_admin");

        if (emailTemplateRepository.findByTemplateName(request.templateName()).isPresent()) {
            throw new IllegalArgumentException("Template name already exists");
        }

        EmailTemplate template = emailTemplateMapper.toEntity(request);
        EmailTemplate saved = emailTemplateRepository.save(template);
        
        createAuditLog("EmailTemplate", saved.getId(), "CREATE");
        return emailTemplateMapper.toResponse(saved);
    }

    @Transactional
    public EmailTemplateResponse updateTemplate(UUID id, UpdateEmailTemplateRequest request) {
        authorizationHelper.requireRole("system_admin");

        EmailTemplate existing = emailTemplateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Template not found"));

        emailTemplateMapper.updateEntityFromRequest(existing, request);
        EmailTemplate saved = emailTemplateRepository.save(existing);
        
        createAuditLog("EmailTemplate", saved.getId(), "UPDATE");
        return emailTemplateMapper.toResponse(saved);
    }

    @Transactional
    public void deleteTemplate(UUID id) {
        authorizationHelper.requireRole("system_admin");
        emailTemplateRepository.deleteById(id);
        createAuditLog("EmailTemplate", id, "DELETE");
    }

    @Transactional
    public void updateResendApiKey(String apiKey) {
        authorizationHelper.requireRole("system_admin");

        SystemSetting setting = systemSettingRepository.findBySettingKey(RESEND_API_KEY_SETTING)
                .orElse(SystemSetting.builder()
                        .settingKey(RESEND_API_KEY_SETTING)
                        .category("email")
                        .build());

        setting.setSettingValue(apiKey);
        systemSettingRepository.save(setting);

        createAuditLog("SystemSetting", setting.getId(), "UPDATE_RESEND_API_KEY");
    }

    private void createAuditLog(String entityType, UUID entityId, String action) {
        AuditLog auditLog = AuditLog.builder()
                .entityType(entityType)
                .entityId(entityId)
                .action(action)
                .changedBy(authorizationHelper.getCurrentUserId())
                .changedAt(OffsetDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
    }
}
