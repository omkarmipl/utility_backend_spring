package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.domain.entity.UtilityProvider;
import com.utilitypro.gumbackend.dto.common.PageResponse;
import com.utilitypro.gumbackend.dto.masterdata.UtilityProviderRequest;
import com.utilitypro.gumbackend.dto.masterdata.UtilityProviderResponse;
import com.utilitypro.gumbackend.mapper.UtilityProviderMapper;
import com.utilitypro.gumbackend.repository.AuditLogRepository;
import com.utilitypro.gumbackend.repository.UtilityProviderRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Utility Provider Service
 * Handles utility provider CRUD (LUCELEC, WASCO, etc.)
 */
@Service
@RequiredArgsConstructor
public class UtilityProviderService {

    private final UtilityProviderRepository utilityProviderRepository;
    private final UtilityProviderMapper utilityProviderMapper;
    private final AuditLogRepository auditLogRepository;
    private final AuthorizationHelper authorizationHelper;

    /**
     * List utility providers with pagination
     */
    @Transactional(readOnly = true)
    public PageResponse<UtilityProviderResponse> listUtilityProviders(Boolean includeInactive, Pageable pageable) {
        Page<UtilityProvider> providers;
        if (Boolean.TRUE.equals(includeInactive)) {
            providers = utilityProviderRepository.findAll(pageable);
        } else {
            providers = utilityProviderRepository.findByIsActiveTrue(pageable);
        }
        return PageResponse.of(providers, utilityProviderMapper::toResponse);
    }

    /**
     * Get utility provider by ID
     */
    @Transactional(readOnly = true)
    public UtilityProviderResponse getUtilityProvider(UUID id) {
        UtilityProvider provider = utilityProviderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utility provider not found"));
        return utilityProviderMapper.toResponse(provider);
    }

    /**
     * Create utility provider
     */
    @Transactional
    public UtilityProviderResponse createUtilityProvider(UtilityProviderRequest request) {
        authorizationHelper.requireRole("system_admin", "mof_admin");

        UtilityProvider provider = utilityProviderMapper.toEntity(request);
        provider.setIsActive(true);
        UtilityProvider saved = utilityProviderRepository.save(provider);

        createAuditLog("UtilityProvider", saved.getId(), "CREATE");
        return utilityProviderMapper.toResponse(saved);
    }

    /**
     * Update utility provider
     */
    @Transactional
    public UtilityProviderResponse updateUtilityProvider(UUID id, UtilityProviderRequest request) {
        authorizationHelper.requireRole("system_admin", "mof_admin");

        UtilityProvider existing = utilityProviderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utility provider not found"));

        utilityProviderMapper.updateEntityFromRequest(existing, request);
        UtilityProvider saved = utilityProviderRepository.save(existing);
        
        createAuditLog("UtilityProvider", saved.getId(), "UPDATE");
        return utilityProviderMapper.toResponse(saved);
    }

    /**
     * Soft delete utility provider
     */
    @Transactional
    public void deleteUtilityProvider(UUID id) {
        authorizationHelper.requireRole("system_admin");

        UtilityProvider provider = utilityProviderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utility provider not found"));

        provider.setIsActive(false);
        utilityProviderRepository.save(provider);
        createAuditLog("UtilityProvider", id, "DELETE");
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
