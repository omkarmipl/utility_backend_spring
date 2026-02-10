package com.utilitypro.gumbackend.dto.business;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Utility Account request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilityAccountRequest {
    
    @NotNull(message = "Provider ID is required")
    private UUID providerId;
    
    @NotNull(message = "Ministry ID is required")
    private UUID ministryId;
    
    private UUID departmentId;
    
    @NotNull(message = "Location ID is required")
    private UUID locationId;
    
    @NotBlank(message = "Provider account number is required")
    @Size(max = 100, message = "Provider account number must be less than 100 characters")
    private String providerAccountNumber;
    
    @NotNull(message = "Account start date is required")
    private LocalDate accountStartDate;
    
    private LocalDate accountEndDate;
    
    @NotBlank(message = "Account status is required")
    @Pattern(regexp = "active|inactive|suspended", message = "Status must be one of: active, inactive, suspended")
    private String accountStatus;
    
    private String notes;
}
