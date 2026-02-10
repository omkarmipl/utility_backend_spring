package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.repository.AuditLogRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Audit Log Service
 * Handles audit log queries with filtering
 */
@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuthorizationHelper authorizationHelper;

    @Transactional(readOnly = true)
    public Page<AuditLog> listAuditLogs(
            String entityType,
            UUID changedBy,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int page,
            int limit) {
        
        authorizationHelper.requireRole("system_admin", "read_only_auditor");
        
        return auditLogRepository.findWithFilters(
                entityType, changedBy, startDate, endDate,
                PageRequest.of(page, limit)
        );
    }
}
