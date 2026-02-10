package com.utilitypro.gumbackend.dto.system;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Season request DTO
 */
public record SeasonRequest(
        
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be less than 100 characters")
        String name,
        
        @NotNull(message = "Start date is required")
        LocalDate startDate,
        
        @NotNull(message = "End date is required")
        LocalDate endDate
) {}
