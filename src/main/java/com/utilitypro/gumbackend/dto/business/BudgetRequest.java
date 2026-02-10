package com.utilitypro.gumbackend.dto.business;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Budget request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRequest {
    
    @NotNull(message = "Ministry ID is required")
    private UUID ministryId;
    
    private UUID departmentId;
    
    @NotNull(message = "Utility type ID is required")
    private UUID utilityTypeId;
    
    @NotBlank(message = "Fiscal year is required")
    @Pattern(regexp = "\\d{4}-\\d{4}", message = "Fiscal year must be in format YYYY-YYYY")
    private String fiscalYear;
    
    @NotNull(message = "Budgeted amount is required")
    @DecimalMin(value = "0.0", message = "Budgeted amount must be positive")
    private BigDecimal budgetedAmount;
    
    private String notes;
}
