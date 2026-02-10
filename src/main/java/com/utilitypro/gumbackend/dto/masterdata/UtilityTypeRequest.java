package com.utilitypro.gumbackend.dto.masterdata;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Utility Type request DTO
 */
public record UtilityTypeRequest(
        
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be less than 100 characters")
        String name,
        
        String description
) {}
