package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.domain.entity.PermissionDefinition;
import com.utilitypro.gumbackend.domain.entity.RolePermission;
import com.utilitypro.gumbackend.repository.AuditLogRepository;
import com.utilitypro.gumbackend.repository.PermissionDefinitionRepository;
import com.utilitypro.gumbackend.repository.RolePermissionRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Permission Service
 * Handles permission definitions and role-permission mappings
 */
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionDefinitionRepository permissionDefinitionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final AuditLogRepository auditLogRepository;
    private final AuthorizationHelper authorizationHelper;

    @Transactional(readOnly = true)
    public com.utilitypro.gumbackend.dto.permission.PermissionListResponse listPermissions() {
        authorizationHelper.requireRole("system_admin", "mof_admin");
        List<PermissionDefinition> permissions = permissionDefinitionRepository.findAll();
        
        List<com.utilitypro.gumbackend.dto.permission.PermissionListResponse.PermissionItem> items = permissions.stream()
            .map(p -> new com.utilitypro.gumbackend.dto.permission.PermissionListResponse.PermissionItem(p.getPermissionKey(), p.getDescription()))
            .collect(java.util.stream.Collectors.toList());
            
        return new com.utilitypro.gumbackend.dto.permission.PermissionListResponse(items);
    }

    @Transactional(readOnly = true)
    public List<com.utilitypro.gumbackend.dto.permission.RolePermissionsResponse> getRolePermissions() {
        authorizationHelper.requireRole("system_admin", "mof_admin");
        List<RolePermission> rolePermissions = rolePermissionRepository.findAll();
        
        // Group by role
        java.util.Map<String, List<String>> permissionsByRole = rolePermissions.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                RolePermission::getRole,
                java.util.stream.Collectors.mapping(RolePermission::getPermission, java.util.stream.Collectors.toList())
            ));
            
        return permissionsByRole.entrySet().stream()
            .map(entry -> new com.utilitypro.gumbackend.dto.permission.RolePermissionsResponse(entry.getKey(), entry.getValue()))
            .collect(java.util.stream.Collectors.toList());
    }

    @Transactional
    public void updateRolePermissions(String roleName, com.utilitypro.gumbackend.dto.permission.UpdateRolePermissionsRequest request) {
        authorizationHelper.requireRole("system_admin");

        // Delete existing permissions for role
        rolePermissionRepository.deleteByRole(roleName);

        // Add new permissions
        if (request.permissions() != null) {
            for (String permissionKey : request.permissions()) {
                RolePermission permission = new RolePermission();
                permission.setRole(roleName);
                permission.setPermission(permissionKey);
                rolePermissionRepository.save(permission);
            }
        }

        createAuditLog("RolePermission", null, "UPDATE_ROLE_PERMISSIONS");
    }

    private void createAuditLog(String entityType, java.util.UUID entityId, String action) {
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
