package com.utilitypro.gumbackend.service.auth;

import com.utilitypro.gumbackend.domain.entity.AppUser;
import com.utilitypro.gumbackend.domain.entity.Profile;
import com.utilitypro.gumbackend.domain.entity.SystemSetting;
import com.utilitypro.gumbackend.domain.entity.UserRole;
import com.utilitypro.gumbackend.domain.enums.Role;
import com.utilitypro.gumbackend.dto.auth.*;
import com.utilitypro.gumbackend.repository.AppUserRepository;
import com.utilitypro.gumbackend.repository.ProfileRepository;
import com.utilitypro.gumbackend.repository.SystemSettingRepository;
import com.utilitypro.gumbackend.repository.UserRoleRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import com.utilitypro.gumbackend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final ProfileRepository profileRepository;
    private final UserRoleRepository userRoleRepository;
    private final SystemSettingRepository systemSettingRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthorizationHelper authorizationHelper;

    private static final int OTP_EXPIRY_MINUTES = 10;
    private static final int OTP_LENGTH = 6;

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        AppUser user = AppUser.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        appUserRepository.save(user);
        
        // Create profile if fullName provided
        if (request.fullName() != null && !request.fullName().isBlank()) {
            Profile profile = Profile.builder()
                    .userId(user.getId())
                    .fullName(request.fullName())
                    .build();
            profileRepository.save(profile);
        }
        
        String token = jwtService.generateToken(user);
        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        AppUser user = appUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        String token = jwtService.generateToken(user);
        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .build();
    }

    /**
     * Logout current user by clearing security context
     * Note: JWT tokens are stateless, so actual invalidation would require token blacklist
     */
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    /**
     * Send password reset OTP to user's email
     * Generates 6-digit OTP and stores in system settings with expiry
     */
    @Transactional
    public void sendPasswordResetOTP(PasswordResetRequest request) {
        AppUser user = appUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Generate 6-digit OTP
        String otp = generateOTP();
        
        // Store OTP in system settings with expiry timestamp
        String otpKey = "password_reset_otp_" + user.getId();
        String otpValue = otp + "|" + LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);
        
        SystemSetting otpSetting = systemSettingRepository.findBySettingKey(otpKey)
                .orElse(SystemSetting.builder()
                        .settingKey(otpKey)
                        .category("password_reset")
                        .build());
        
        otpSetting.setSettingValue(otpValue);
        systemSettingRepository.save(otpSetting);

        // TODO: Send email with OTP using email service
        // emailService.sendPasswordResetOTP(user.getEmail(), otp);
    }

    /**
     * Verify OTP and reset password
     * Auto-login user after successful password reset
     */
    @Transactional
    public LoginResponse verifyPasswordResetOTP(PasswordResetVerifyRequest request) {
        AppUser user = appUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Retrieve and validate OTP
        String otpKey = "password_reset_otp_" + user.getId();
        SystemSetting otpSetting = systemSettingRepository.findBySettingKey(otpKey)
                .orElseThrow(() -> new IllegalArgumentException("No OTP found for this user"));

        String[] otpParts = otpSetting.getSettingValue().split("\\|");
        String storedOtp = otpParts[0];
        LocalDateTime expiryTime = LocalDateTime.parse(otpParts[1]);

        // Validate OTP
        if (!storedOtp.equals(request.otp())) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        // Check expiry
        if (LocalDateTime.now().isAfter(expiryTime)) {
            systemSettingRepository.delete(otpSetting);
            throw new IllegalArgumentException("OTP has expired");
        }

        // Reset password
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        appUserRepository.save(user);

        // Delete OTP setting
        systemSettingRepository.delete(otpSetting);

        // Auto-login and return token
        String token = jwtService.generateToken(user);
        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .build();
    }

    /**
     * Get current user session with profile and roles
     */
    @Transactional(readOnly = true)
    public SessionResponse getCurrentSession() {
        AppUser user = authorizationHelper.getCurrentUser();
        
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElse(null);
        
        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        List<String> roles = userRoles.stream()
                .map(ur -> ur.getRoleName())
                .collect(Collectors.toList());

        return SessionResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .fullName(profile != null ? profile.getFullName() : null)
                .roles(roles)
                .scopes(List.of()) // TODO: Load and map scopes
                .build();
    }

    /**
     * Generate random 6-digit OTP
     */
    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
