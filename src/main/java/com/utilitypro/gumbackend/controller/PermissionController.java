package com.utilitypro.gumbackend.controller;

import com.utilitypro.gumbackend.dto.permission.*;
import com.utilitypro.gumbackend.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * REST Controller for Permissions and Role Management
 * Handles permission definitions and role-to-permission mappings
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * GET /api/permissions
     * List all permission definitions
     */
    @GetMapping("/permissions")
    @PreAuthorize("hasAnyRole('system_admin', 'mof_admin')")
    public ResponseEntity<PermissionListResponse> listPermissions() {
        PermissionListResponse response = permissionService.listPermissions();
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/roles/permissions
     * Get role-to-permission mappings
     */
    @GetMapping("/roles/permissions")
    @PreAuthorize("hasAnyRole('system_admin', 'mof_admin')")
    public ResponseEntity<java.util.List<RolePermissionsResponse>> getRolePermissions() {
        java.util.List<RolePermissionsResponse> response = permissionService.getRolePermissions();
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/roles/:role/permissions
     * Update permissions for a role
     */
    @PutMapping("/roles/{role}/permissions")
    @PreAuthorize("hasRole('system_admin')")
    public ResponseEntity<Void> updateRolePermissions(
            @PathVariable String role,
            @Valid @RequestBody UpdateRolePermissionsRequest request) {
        permissionService.updateRolePermissions(role, request);
        return ResponseEntity.noContent().build();
    }
}
