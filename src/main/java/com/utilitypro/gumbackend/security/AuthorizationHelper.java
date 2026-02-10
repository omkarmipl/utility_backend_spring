package com.utilitypro.gumbackend.security;

import com.utilitypro.gumbackend.domain.entity.AppUser;
import com.utilitypro.gumbackend.domain.entity.UserScope;
import com.utilitypro.gumbackend.repository.UserScopeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Authorization Helper Utility
 * Centralized authorization checks for role-based and scope-based access control
 */
@Component
@RequiredArgsConstructor
public class AuthorizationHelper {

    private final UserScopeRepository userScopeRepository;

    /**
     * Get currently authenticated user from SecurityContext
     * @return Current authenticated AppUser
     * @throws AccessDeniedException if no user is authenticated
     */
    public AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || 
            !(authentication.getPrincipal() instanceof AppUser)) {
            throw new AccessDeniedException("No authenticated user found");
        }
        return (AppUser) authentication.getPrincipal();
    }

    /**
     * Get current user ID
     * @return UUID of current user
     */
    public UUID getCurrentUserId() {
        return getCurrentUser().getId();
    }

    /**
     * Check if current user has any of the specified roles
     * @param roles Roles to check (e.g., "system_admin", "mof_admin")
     * @return true if user has any of the roles
     */
    public boolean hasRole(String... roles) {
        AppUser user = getCurrentUser();
        String userRole = user.getRole().name().toLowerCase();
        
        for (String role : roles) {
            if (userRole.equals(role.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if current user is system administrator
     * @return true if user has system_admin role
     */
    public boolean isSystemAdmin() {
        return hasRole("system_admin");
    }

    /**
     * Check if current user is MoF administrator
     * @return true if user has mof_admin role
     */
    public boolean isMofAdmin() {
        return hasRole("mof_admin");
    }

    /**
     * Check if current user is ministry administrator
     * @return true if user has ministry_admin role
     */
    public boolean isMinistryAdmin() {
        return hasRole("ministry_admin");
    }

    /**
     * Check if current user is any type of admin
     * @return true if user has system_admin, mof_admin, or ministry_admin role
     */
    public boolean isAdmin() {
        return hasRole("system_admin", "mof_admin", "ministry_admin");
    }

    /**
     * Verify that current user owns the specified resource
     * @param userId User ID to check ownership against
     * @throws AccessDeniedException if current user doesn't own the resource
     */
    public void checkOwnership(UUID userId) {
        if (!getCurrentUserId().equals(userId)) {
            throw new AccessDeniedException("User does not have access to this resource");
        }
    }

    /**
     * Get current user's ministry/department scopes
     * @return List of UserScope entities
     */
    public List<UserScope> getUserScopes() {
        return userScopeRepository.findByUserId(getCurrentUserId());
    }

    /**
     * Check if current user has access to specified ministry
     * System admins and MoF admins have access to all ministries
     * Other users must have explicit scope
     * 
     * @param ministryId Ministry ID to check access for
     * @return true if user has access
     */
    public boolean hasMinistryAccess(UUID ministryId) {
        // System admin and MoF admin have access to all ministries
        if (isSystemAdmin() || isMofAdmin()) {
            return true;
        }

        // Check if user has scope for this ministry
        List<UserScope> scopes = getUserScopes();
        
        // If user has no scopes, deny access
        if (scopes.isEmpty()) {
            return false;
        }

        // Check if any scope matches the ministry (null ministry_id means access to all)
        return scopes.stream()
                .anyMatch(scope -> scope.getMinistry() == null || 
                                 scope.getMinistry().getId().equals(ministryId));
    }

    /**
     * Check if current user has access to specified department
     * System admins and MoF admins have access to all departments
     * Other users must have explicit scope
     * 
     * @param departmentId Department ID to check access for
     * @param ministryId Ministry ID that owns the department
     * @return true if user has access
     */
    public boolean hasDepartmentAccess(UUID departmentId, UUID ministryId) {
        // System admin and MoF admin have access to all departments
        if (isSystemAdmin() || isMofAdmin()) {
            return true;
        }

        // Check if user has scope for this department or ministry
        List<UserScope> scopes = getUserScopes();
        
        if (scopes.isEmpty()) {
            return false;
        }

        return scopes.stream()
                .anyMatch(scope -> {
                    // Null ministry means access to all
                    if (scope.getMinistry() == null) {
                        return true;
                    }
                    // Ministry matches and department is null (access to all departments in ministry)
                    if (scope.getMinistry().getId().equals(ministryId) && scope.getDepartment() == null) {
                        return true;
                    }
                    // Exact department match
                    return scope.getDepartment() != null && 
                           scope.getDepartment().getId().equals(departmentId);
                });
    }

    /**
     * Require that user has any of the specified roles
     * @param roles Required roles
     * @throws AccessDeniedException if user doesn't have any of the roles
     */
    public void requireRole(String... roles) {
        if (!hasRole(roles)) {
            throw new AccessDeniedException("User does not have required role");
        }
    }

    /**
     * Require that user has access to specified ministry
     * @param ministryId Ministry ID
     * @throws AccessDeniedException if user doesn't have access
     */
    public void requireMinistryAccess(UUID ministryId) {
        if (!hasMinistryAccess(ministryId)) {
            throw new AccessDeniedException("User does not have access to this ministry");
        }
    }

    /**
     * Require that user has access to specified department
     * @param departmentId Department ID
     * @param ministryId Ministry ID
     * @throws AccessDeniedException if user doesn't have access
     */
    public void requireDepartmentAccess(UUID departmentId, UUID ministryId) {
        if (!hasDepartmentAccess(departmentId, ministryId)) {
            throw new AccessDeniedException("User does not have access to this department");
        }
    }
}
