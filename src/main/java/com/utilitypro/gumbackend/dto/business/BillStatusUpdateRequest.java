package com.utilitypro.gumbackend.dto.business;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Bill status update request DTO
 */
public record BillStatusUpdateRequest(
        
        @NotBlank(message = "Status is required")
        @Pattern(regexp = "draft|submitted|approved|paid", message = "Status must be one of: draft, submitted, approved, paid")
        String status
) {}
