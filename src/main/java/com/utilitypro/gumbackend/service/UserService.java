package com.utilitypro.gumbackend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utilitypro.gumbackend.domain.entity.Profile;
import com.utilitypro.gumbackend.domain.entity.UserRole;
import com.utilitypro.gumbackend.domain.entity.UserScope;
import com.utilitypro.gumbackend.repository.ProfileRepository;
import com.utilitypro.gumbackend.repository.UserRoleRepository;
import com.utilitypro.gumbackend.repository.UserScopeRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;

import lombok.RequiredArgsConstructor;

/**
 * User Service
 * Handles current user profile, roles, and scopes
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final ProfileRepository profileRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserScopeRepository userScopeRepository;
    private final AuthorizationHelper authorizationHelper;


    /**
     * Get roles for current authenticated user
     * @return UserRolesResponse
     */
    @Transactional(readOnly = true)
    public com.utilitypro.gumbackend.dto.user.UserRolesResponse getCurrentUserRoles() {
        UUID userId = authorizationHelper.getCurrentUserId();
        List<UserRole> roles = userRoleRepository.findByUserId(userId);
        List<String> roleNames = roles.stream()
                .map(UserRole::getRoleName)
                .collect(java.util.stream.Collectors.toList());
        return new com.utilitypro.gumbackend.dto.user.UserRolesResponse(roleNames);
    }

    /**
     * Get profile for current authenticated user
     * @return UserProfileResponse
     */
    @Transactional(readOnly = true)
    public com.utilitypro.gumbackend.dto.user.UserProfileResponse getCurrentUserProfile() {
        UUID userId = authorizationHelper.getCurrentUserId();
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user"));
        
        return com.utilitypro.gumbackend.dto.user.UserProfileResponse.builder()
                .userId(profile.getUserId())
                .fullName(profile.getFullName())
                .phoneNumber(profile.getPhoneNumber())
                .jobTitle(profile.getJobTitle())
                .department(profile.getDepartment())
                .build();
    }

    /**
     * Get current user's ministry/department scopes
     * @return UserScopesResponse
     */
    @Transactional(readOnly = true)
    public com.utilitypro.gumbackend.dto.user.UserScopesResponse getCurrentUserScopes() {
        UUID userId = authorizationHelper.getCurrentUserId();
        List<UserScope> scopes = userScopeRepository.findByUserId(userId);
        
        List<com.utilitypro.gumbackend.dto.user.UserScopesResponse.ScopeDTO> scopeDTOs = scopes.stream()
                .map(scope -> com.utilitypro.gumbackend.dto.user.UserScopesResponse.ScopeDTO.builder()
                        .ministryId(scope.getMinistry() != null ? scope.getMinistry().getId() : null)
                        .ministryName(scope.getMinistry() != null ? scope.getMinistry().getName() : null)
                        .departmentId(scope.getDepartment() != null ? scope.getDepartment().getId() : null)
                        .departmentName(scope.getDepartment() != null ? scope.getDepartment().getName() : null)
                        .build())
                .collect(java.util.stream.Collectors.toList());
                
        return new com.utilitypro.gumbackend.dto.user.UserScopesResponse(scopeDTOs);
    }
}
