package com.utilitypro.gumbackend.controller;

import com.utilitypro.gumbackend.dto.user.*;
import com.utilitypro.gumbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for User Profile, Roles, and Scopes Management
 * Handles current user operations
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * GET /api/users/roles
     * Get roles for authenticated user
     */
    @GetMapping("/roles")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserRolesResponse> getUserRoles() {
        UserRolesResponse response = userService.getCurrentUserRoles();
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/users/profile
     * Get profile for authenticated user
     */
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileResponse> getUserProfile() {
        UserProfileResponse response = userService.getCurrentUserProfile();
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/users/scopes
     * Get current user's ministry/department scopes
     */
    @GetMapping("/scopes")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserScopesResponse> getUserScopes() {
        UserScopesResponse response = userService.getCurrentUserScopes();
        return ResponseEntity.ok(response);
    }
}
