package com.utilitypro.gumbackend.dto.masterdata;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * Location request DTO
 */
public record LocationRequest(
        
        @NotNull(message = "Ministry ID is required")
        UUID ministryId,
        
        UUID departmentId,
        
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be less than 255 characters")
        String name,
        
        String address,
        
        String city,
        
        String district
) {}
