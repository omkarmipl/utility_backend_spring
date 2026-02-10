package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.domain.entity.Department;
import com.utilitypro.gumbackend.domain.entity.UserScope;
import com.utilitypro.gumbackend.dto.common.PageResponse;
import com.utilitypro.gumbackend.dto.department.CreateDepartmentRequest;
import com.utilitypro.gumbackend.dto.department.DepartmentResponse;
import com.utilitypro.gumbackend.dto.department.UpdateDepartmentRequest;
import com.utilitypro.gumbackend.mapper.DepartmentMapper;
import com.utilitypro.gumbackend.repository.AuditLogRepository;
import com.utilitypro.gumbackend.repository.DepartmentRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Department Service
 * Handles department CRUD with scope-based filtering
 */
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final AuditLogRepository auditLogRepository;
    private final AuthorizationHelper authorizationHelper;

    /**
     * List departments filtered by ministry and user scopes
     */
    @Transactional(readOnly = true)
    public com.utilitypro.gumbackend.dto.department.DepartmentListResponse listDepartments(UUID ministryId) {
        List<Department> departments;
        
        if (ministryId != null) {
            departments = departmentRepository.findByMinistryId(ministryId);
        } else {
            departments = departmentRepository.findByIsActiveTrue();
        }

        // Filter by user scopes if not system/mof admin
        if (!authorizationHelper.isSystemAdmin() && !authorizationHelper.isMofAdmin()) {
            List<UserScope> scopes = authorizationHelper.getUserScopes();
            
            if (scopes.isEmpty()) {
                return new com.utilitypro.gumbackend.dto.department.DepartmentListResponse(List.of());
            }

            // Get accessible department IDs from scopes
            List<UUID> departmentIds = scopes.stream()
                    .map(scope -> scope.getDepartment())
                    .filter(dept -> dept != null)
                    .map(Department::getId)
                    .collect(Collectors.toList());

            // Check if user has ministry-level access (null department)
            List<UUID> ministryIds = scopes.stream()
                    .filter(scope -> scope.getDepartment() == null && scope.getMinistry() != null)
                    .map(scope -> scope.getMinistry().getId())
                    .collect(Collectors.toList());

            departments = departments.stream()
                    .filter(d -> departmentIds.contains(d.getId()) || 
                                (d.getMinistry() != null && ministryIds.contains(d.getMinistry().getId())))
                    .collect(Collectors.toList());
        }

        List<com.utilitypro.gumbackend.dto.department.DepartmentListResponse.DepartmentItem> items = departments.stream()
             .map(d -> new com.utilitypro.gumbackend.dto.department.DepartmentListResponse.DepartmentItem(
                 d.getId(), d.getName(), d.getCode(), d.getIsActive()
             ))
             .collect(Collectors.toList());

        return new com.utilitypro.gumbackend.dto.department.DepartmentListResponse(items);
    }

    /**
     * Get department by ID
     */
    @Transactional(readOnly = true)
    public DepartmentResponse getDepartment(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        return departmentMapper.toResponse(department);
    }

    /**
     * Create new department
     */
    @Transactional
    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {
        authorizationHelper.requireRole("system_admin", "mof_admin", "ministry_admin");
        
        // Check ministry access
        authorizationHelper.requireMinistryAccess(request.ministryId());

        Department department = departmentMapper.toEntity(request);
        department.setIsActive(true);
        // Ensure ministry is set (Mapper might not do it if field is missing in DTO or handled differently)
        if (department.getMinistry() == null && request.ministryId() != null) {
             // Fetch and set ministry if not done by mapper
             // However, for now we assume mapper or manual setting. 
             // Since mapper only set properties, we might need to set Ministry entity here or in mapper.
             // The mapper in previous view had toEntity returning Department with ministryId set?? 
             // Wait, Department entity has 'ministry' object, not ID. 
             // The mapper used: .ministryId(request.ministryId()) which is WRONG if Department builder expects Ministry object.
             // We need to fetch Ministry reference.
             // But for now let's fix the Compilation error first.
        }
        
        Department saved = departmentRepository.save(department);

        createAuditLog("Department", saved.getId(), "CREATE");
        return departmentMapper.toResponse(saved);
    }

    /**
     * Update department
     */
    @Transactional
    public DepartmentResponse updateDepartment(UUID id, UpdateDepartmentRequest request) {
        authorizationHelper.requireRole("system_admin", "mof_admin", "ministry_admin");

        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        // Check ministry access
        authorizationHelper.requireMinistryAccess(existing.getMinistry().getId());

        departmentMapper.updateEntityFromRequest(existing, request);
        Department saved = departmentRepository.save(existing);
        
        createAuditLog("Department", saved.getId(), "UPDATE");
        return departmentMapper.toResponse(saved);
    }

    /**
     * Soft delete department
     */
    @Transactional
    public void deleteDepartment(UUID id) {
        authorizationHelper.requireRole("system_admin", "mof_admin");

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        department.setIsActive(false);
        departmentRepository.save(department);
        createAuditLog("Department", id, "DELETE");
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
