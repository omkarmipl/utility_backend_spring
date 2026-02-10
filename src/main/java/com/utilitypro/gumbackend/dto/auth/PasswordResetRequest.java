package com.utilitypro.gumbackend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Password reset request DTO (send OTP)
 */
public record PasswordResetRequest(
        
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email
) {}
