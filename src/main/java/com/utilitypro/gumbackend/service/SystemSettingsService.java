package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.domain.entity.SystemSetting;
import com.utilitypro.gumbackend.dto.common.PageResponse;
import com.utilitypro.gumbackend.dto.system.SystemSettingRequest;
import com.utilitypro.gumbackend.dto.system.SystemSettingResponse;
import com.utilitypro.gumbackend.mapper.SystemSettingMapper;
import com.utilitypro.gumbackend.repository.AuditLogRepository;
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
 * System Settings Service
 * Handles system-wide configuration and branding
 */
@Service
@RequiredArgsConstructor
public class SystemSettingsService {

    private final SystemSettingRepository systemSettingRepository;
    private final SystemSettingMapper systemSettingMapper;
    private final AuditLogRepository auditLogRepository;
    private final AuthorizationHelper authorizationHelper;

    @Transactional(readOnly = true)
    public PageResponse<SystemSettingResponse> listSettings(Pageable pageable) {
        authorizationHelper.requireRole("system_admin");
        Page<SystemSetting> settings = systemSettingRepository.findAll(pageable);
        return PageResponse.of(settings, systemSettingMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public SystemSettingResponse getSetting(String settingKey) {
        authorizationHelper.requireRole("system_admin");
        SystemSetting setting = systemSettingRepository.findBySettingKey(settingKey)
                .orElseThrow(() -> new IllegalArgumentException("Setting not found"));
        return systemSettingMapper.toResponse(setting);
    }

    @Transactional
    public SystemSettingResponse updateSetting(String settingKey, SystemSettingRequest request) {
        authorizationHelper.requireRole("system_admin");

        SystemSetting setting = systemSettingRepository.findBySettingKey(settingKey)
                .orElseThrow(() -> new IllegalArgumentException("Setting not found"));

        systemSettingMapper.updateEntityFromRequest(setting, request);
        SystemSetting saved = systemSettingRepository.save(setting);

        createAuditLog("SystemSetting", saved.getId(), "UPDATE");
        return systemSettingMapper.toResponse(saved);
    }

    @Transactional
    public SystemSettingResponse createSetting(SystemSettingRequest request) {
        authorizationHelper.requireRole("system_admin");

        if (systemSettingRepository.findBySettingKey(request.settingKey()).isPresent()) {
            throw new IllegalArgumentException("Setting key already exists");
        }

        SystemSetting setting = systemSettingMapper.toEntity(request);
        SystemSetting saved = systemSettingRepository.save(setting);
        
        createAuditLog("SystemSetting", saved.getId(), "CREATE");
        return systemSettingMapper.toResponse(saved);
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
