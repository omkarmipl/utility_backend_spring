package com.utilitypro.gumbackend.dto.masterdata;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Ministry request DTO
 */
public record MinistryRequest(
        
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be less than 255 characters")
        String name,
        
        @NotBlank(message = "Code is required")
        @Size(max = 50, message = "Code must be less than 50 characters")
        String code,
        
        String description
) {}
