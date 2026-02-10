package com.utilitypro.gumbackend.dto.system;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * System setting request DTO
 */
public record SystemSettingRequest(
        
        @NotBlank(message = "Setting key is required")
        @Size(max = 100, message = "Setting key must be less than 100 characters")
        String settingKey,
        
        @NotBlank(message = "Setting value is required")
        String settingValue,
        
        String category
) {}
