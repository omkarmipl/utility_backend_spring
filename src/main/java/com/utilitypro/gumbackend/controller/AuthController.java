package com.utilitypro.gumbackend.controller;

import com.utilitypro.gumbackend.dto.auth.*;
import com.utilitypro.gumbackend.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller
 * Handles user authentication, registration, and password management
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * User registration
     */
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        LoginResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    /**
     * User login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * User logout
     */
    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    /**
     * Send password reset OTP
     */
    @PostMapping("/password-reset/send-otp")
    public ResponseEntity<Void> sendPasswordResetOTP(@Valid @RequestBody PasswordResetRequest request) {
        authService.sendPasswordResetOTP(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Verify OTP and reset password
     */
    @PostMapping("/password-reset/verify-otp")
    public ResponseEntity<LoginResponse> verifyPasswordResetOTP(@Valid @RequestBody PasswordResetVerifyRequest request) {
        LoginResponse response = authService.verifyPasswordResetOTP(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get current user session
     */
    @GetMapping("/session")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SessionResponse> getCurrentSession() {
        SessionResponse response = authService.getCurrentSession();
        return ResponseEntity.ok(response);
    }
}
