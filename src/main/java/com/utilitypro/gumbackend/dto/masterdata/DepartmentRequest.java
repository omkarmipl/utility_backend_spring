package com.utilitypro.gumbackend.dto.masterdata;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * Department request DTO
 */
public record DepartmentRequest(
        
        @NotNull(message = "Ministry ID is required")
        UUID ministryId,
        
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be less than 255 characters")
        String name,
        
        String description
) {}
