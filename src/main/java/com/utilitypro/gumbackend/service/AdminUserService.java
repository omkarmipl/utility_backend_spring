package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AppUser;
import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.domain.entity.Profile;
import com.utilitypro.gumbackend.domain.entity.UserRole;
import com.utilitypro.gumbackend.domain.entity.UserScope;
import com.utilitypro.gumbackend.domain.enums.Role;
import com.utilitypro.gumbackend.dto.admin.*;
import com.utilitypro.gumbackend.repository.*;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Admin User Service
 * Handles comprehensive user management for system administrators
 */
@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AppUserRepository appUserRepository;
    private final ProfileRepository profileRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserScopeRepository userScopeRepository;
    private final MinistryRepository ministryRepository;
    private final DepartmentRepository departmentRepository;
    private final AuditLogRepository auditLogRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorizationHelper authorizationHelper;

    @Transactional(readOnly = true)
    public UserListResponse listUsers(Integer page, Integer limit) {
        authorizationHelper.requireRole("system_admin");
        int pageNum = (page != null && page >= 0) ? page : 0;
        int limitNum = (limit != null && limit > 0) ? limit : 20;

        Page<AppUser> usersPage = appUserRepository.findAll(PageRequest.of(pageNum, limitNum));
        
        List<UserListResponse.UserSummary> summaries = usersPage.getContent().stream()
            .map(user -> {
                Profile profile = profileRepository.findByUserId(user.getId()).orElse(null);
                String fullName = (profile != null) ? profile.getFullName() : null;
                
                List<String> roles = userRoleRepository.findByUserId(user.getId()).stream()
                        .map(UserRole::getRoleName).collect(Collectors.toList());
                        
                return new UserListResponse.UserSummary(
                    user.getId(), 
                    user.getEmail(), 
                    fullName, 
                    roles, 
                    true, // assuming enabled is true
                    null // lastLogin
                );
            }).collect(Collectors.toList());

        return new UserListResponse(summaries, (int)usersPage.getTotalElements(), pageNum, limitNum);
    }

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        authorizationHelper.requireRole("system_admin");

        if (appUserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        AppUser user = AppUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER) // Default role
                .build();
        
        AppUser savedUser = appUserRepository.save(user);

        // Create profile
        if (request.getFullName() != null) {
            Profile profile = Profile.builder()
                    .userId(savedUser.getId())
                    .fullName(request.getFullName())
                    .phoneNumber(request.getPhoneNumber())
                    .jobTitle(request.getJobTitle())
                    .department(request.getDepartment())
                    .email(request.getEmail()) // Profile needs email too
                    .firstName(extractFirstName(request.getFullName()))
                    .lastName(extractLastName(request.getFullName()))
                    .build();
            profileRepository.save(profile);
        }

        // Assign roles
        if (request.getRoles() != null) {
            for (String roleName : request.getRoles()) {
                UserRole role = UserRole.builder()
                        .userId(savedUser.getId())
                        .roleName(roleName)
                        .build();
                userRoleRepository.save(role);
            }
        }

        // Create scopes
        if (request.getScopes() != null) {
            for (CreateUserRequest.ScopeDTO scopeDto : request.getScopes()) {
                UserScope.UserScopeBuilder scopeBuilder = UserScope.builder()
                        .userId(savedUser.getId());
                
                if (scopeDto.getMinistryId() != null) {
                    scopeBuilder.ministry(ministryRepository.findById(scopeDto.getMinistryId()).orElse(null));
                }
                
                if (scopeDto.getDepartmentId() != null) {
                    scopeBuilder.department(departmentRepository.findById(scopeDto.getDepartmentId()).orElse(null));
                }
                
                userScopeRepository.save(scopeBuilder.build());
            }
        }

        createAuditLog("AppUser", savedUser.getId(), "CREATE");
        
        return new CreateUserResponse(savedUser.getId(), savedUser.getEmail(), request.getFullName(), true);
    }

    @Transactional
    public UpdateUserResponse updateUser(UUID userId, UpdateUserRequest request) {
        authorizationHelper.requireRole("system_admin");

        AppUser existing = appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getEmail() != null && !request.getEmail().equals(existing.getEmail())) {
             if (appUserRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already exists");
             }
             existing.setEmail(request.getEmail());
        }

        AppUser saved = appUserRepository.save(existing);
        
        // Update Profile
        Profile profile = profileRepository.findByUserId(userId).orElse(
            Profile.builder().userId(userId).email(saved.getEmail()).firstName("").lastName("").build()
        );
        
        if (request.getFullName() != null) {
            profile.setFullName(request.getFullName());
            profile.setFirstName(extractFirstName(request.getFullName()));
            profile.setLastName(extractLastName(request.getFullName()));
        }
        if (request.getPhoneNumber() != null) profile.setPhoneNumber(request.getPhoneNumber());
        if (request.getJobTitle() != null) profile.setJobTitle(request.getJobTitle());
        if (request.getDepartment() != null) profile.setDepartment(request.getDepartment());
        
        profileRepository.save(profile);

        createAuditLog("AppUser", saved.getId(), "UPDATE");
        
        return new UpdateUserResponse(saved.getId(), saved.getEmail(), profile.getFullName(), true);
    }

    @Transactional
    public void updateUserRoles(UUID userId, UpdateUserRolesRequest request) {
        authorizationHelper.requireRole("system_admin");

        // Delete existing roles
        userRoleRepository.deleteByUserId(userId);

        // Add new roles
        if (request.roles() != null) {
            for (String roleName : request.roles()) {
                UserRole role = UserRole.builder()
                        .userId(userId)
                        .roleName(roleName)
                        .build();
                userRoleRepository.save(role);
            }
        }

        createAuditLog("AppUser", userId, "UPDATE_ROLES");
    }

    @Transactional
    public void updateUserScopes(UUID userId, UpdateUserScopesRequest request) {
        authorizationHelper.requireRole("system_admin");

        // Delete existing scopes
        userScopeRepository.deleteByUserId(userId);

        if (request.ministryIds() != null) {
             for (UUID ministryId : request.ministryIds()) {
                 UserScope scope = UserScope.builder()
                        .userId(userId)
                        .ministry(ministryRepository.findById(ministryId).orElse(null))
                        .build();
                 userScopeRepository.save(scope);
             }
        }
        
        if (request.departmentIds() != null) {
             for (UUID departmentId : request.departmentIds()) {
                 UserScope scope = UserScope.builder()
                        .userId(userId)
                        .department(departmentRepository.findById(departmentId).orElse(null))
                        .build();
                 userScopeRepository.save(scope);
             }
        }

        createAuditLog("AppUser", userId, "UPDATE_SCOPES");
    }

    @Transactional
    public PasswordResetResponse resetUserPassword(UUID userId) {
        authorizationHelper.requireRole("system_admin");

        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String tempPassword = generateTemporaryPassword();
        user.setPassword(passwordEncoder.encode(tempPassword));
        appUserRepository.save(user);

        createAuditLog("AppUser", userId, "PASSWORD_RESET");
        
        return new PasswordResetResponse("SUCCESS", "Password reset successfully. Temporary password: " + tempPassword);
    }

    @Transactional(readOnly = true)
    public UserSessionsResponse getUserSessions(UUID userId) {
        authorizationHelper.requireRole("system_admin");
        // Mock implementation
        return new UserSessionsResponse(List.of()); 
    }

    @Transactional
    public void forceLogoutUserSessions(UUID userId, ForceLogoutRequest request) {
        authorizationHelper.requireRole("system_admin");

        if (userId.equals(authorizationHelper.getCurrentUserId())) {
            throw new IllegalArgumentException("Cannot force logout yourself");
        }
        
        createAuditLog("AppUser", userId, "FORCE_LOGOUT");
    }

    private String generateTemporaryPassword() {
        return UUID.randomUUID().toString().substring(0, 12);
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
    
    private String extractFirstName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) return "";
        String[] parts = fullName.trim().split("\\s+", 2);
        return parts[0];
    }

    private String extractLastName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) return "";
        String[] parts = fullName.trim().split("\\s+", 2);
        return parts.length > 1 ? parts[1] : "";
    }
}
