package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.domain.entity.UtilityType;
import com.utilitypro.gumbackend.dto.common.PageResponse;
import com.utilitypro.gumbackend.dto.masterdata.UtilityTypeRequest;
import com.utilitypro.gumbackend.dto.masterdata.UtilityTypeResponse;
import com.utilitypro.gumbackend.mapper.UtilityTypeMapper;
import com.utilitypro.gumbackend.repository.AuditLogRepository;
import com.utilitypro.gumbackend.repository.UtilityTypeRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Utility Type Service
 * Handles utility type CRUD (electricity, water, etc.)
 */
@Service
@RequiredArgsConstructor
public class UtilityTypeService {

    private final UtilityTypeRepository utilityTypeRepository;
    private final UtilityTypeMapper utilityTypeMapper;
    private final AuditLogRepository auditLogRepository;
    private final AuthorizationHelper authorizationHelper;

    /**
     * List utility types with pagination
     */
    @Transactional(readOnly = true)
    public PageResponse<UtilityTypeResponse> listUtilityTypes(Boolean includeInactive, Pageable pageable) {
        Page<UtilityType> types;
        if (Boolean.TRUE.equals(includeInactive)) {
            types = utilityTypeRepository.findAll(pageable);
        } else {
            types = utilityTypeRepository.findByIsActiveTrue(pageable);
        }
        return PageResponse.of(types, utilityTypeMapper::toResponse);
    }

    /**
     * Get utility type by ID
     */
    @Transactional(readOnly = true)
    public UtilityTypeResponse getUtilityType(UUID id) {
        UtilityType type = utilityTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utility type not found"));
        return utilityTypeMapper.toResponse(type);
    }

    /**
     * Create utility type (system_admin only)
     */
    @Transactional
    public UtilityTypeResponse createUtilityType(UtilityTypeRequest request) {
        authorizationHelper.requireRole("system_admin");

        UtilityType utilityType = utilityTypeMapper.toEntity(request);
        utilityType.setIsActive(true);
        UtilityType saved = utilityTypeRepository.save(utilityType);

        createAuditLog("UtilityType", saved.getId(), "CREATE");
        return utilityTypeMapper.toResponse(saved);
    }

    /**
     * Update utility type (system_admin only)
     */
    @Transactional
    public UtilityTypeResponse updateUtilityType(UUID id, UtilityTypeRequest request) {
        authorizationHelper.requireRole("system_admin");

        UtilityType existing = utilityTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utility type not found"));

        utilityTypeMapper.updateEntityFromRequest(existing, request);
        UtilityType saved = utilityTypeRepository.save(existing);
        
        createAuditLog("UtilityType", saved.getId(), "UPDATE");
        return utilityTypeMapper.toResponse(saved);
    }

    /**
     * Soft delete utility type (system_admin only)
     */
    @Transactional
    public void deleteUtilityType(UUID id) {
        authorizationHelper.requireRole("system_admin");

        UtilityType utilityType = utilityTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utility type not found"));

        utilityType.setIsActive(false);
        utilityTypeRepository.save(utilityType);
        createAuditLog("UtilityType", id, "DELETE");
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
