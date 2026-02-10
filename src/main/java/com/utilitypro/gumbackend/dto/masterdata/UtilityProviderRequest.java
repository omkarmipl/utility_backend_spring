package com.utilitypro.gumbackend.dto.masterdata;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * Utility Provider request DTO
 */
public record UtilityProviderRequest(
        
        @NotNull(message = "Utility type ID is required")
        UUID utilityTypeId,
        
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be less than 255 characters")
        String name,
        
        String contactInfo,
        
        String address
) {}
