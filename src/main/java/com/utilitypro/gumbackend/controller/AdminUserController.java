package com.utilitypro.gumbackend.controller;

import com.utilitypro.gumbackend.dto.admin.*;
import com.utilitypro.gumbackend.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

/**
 * REST Controller for Admin User Management
 * Handles user CRUD, role/scope assignments, password resets, and session management
 * Requires system_admin role
 */
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('system_admin')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * GET /api/admin/users
     * List all users with roles and scopes
     */
    @GetMapping
    public ResponseEntity<UserListResponse> listUsers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        UserListResponse response = adminUserService.listUsers(page, limit);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/admin/users
     * Create new user (admin only)
     */
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse response = adminUserService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * PUT /api/admin/users/:userId
     * Update user details
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateUserRequest request) {
        UpdateUserResponse response = adminUserService.updateUser(userId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/admin/users/:userId/roles
     * Update user roles
     */
    @PutMapping("/{userId}/roles")
    public ResponseEntity<Void> updateUserRoles(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateUserRolesRequest request) {
        adminUserService.updateUserRoles(userId, request);
        return ResponseEntity.noContent().build();
    }

    /**
     * PUT /api/admin/users/:userId/scopes
     * Update user scopes (ministry/department access)
     */
    @PutMapping("/{userId}/scopes")
    public ResponseEntity<Void> updateUserScopes(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateUserScopesRequest request) {
        adminUserService.updateUserScopes(userId, request);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/admin/users/:userId/password-reset
     * Admin reset user password
     */
    @PostMapping("/{userId}/password-reset")
    public ResponseEntity<PasswordResetResponse> resetUserPassword(@PathVariable UUID userId) {
        PasswordResetResponse response = adminUserService.resetUserPassword(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/admin/users/:userId/sessions
     * List active sessions for a user
     */
    @GetMapping("/{userId}/sessions")
    public ResponseEntity<UserSessionsResponse> getUserSessions(@PathVariable UUID userId) {
        UserSessionsResponse response = adminUserService.getUserSessions(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/admin/users/:userId/sessions/logout
     * Force logout user sessions
     */
    @PostMapping("/{userId}/sessions/logout")
    public ResponseEntity<Void> forceLogoutUserSessions(
            @PathVariable UUID userId,
            @Valid @RequestBody ForceLogoutRequest request) {
        adminUserService.forceLogoutUserSessions(userId, request);
        return ResponseEntity.noContent().build();
    }
}
