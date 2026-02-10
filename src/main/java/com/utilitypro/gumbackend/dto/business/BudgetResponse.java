package com.utilitypro.gumbackend.dto.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Budget response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponse {
    private UUID id;
    private UUID ministryId;
    private UUID departmentId;
    private UUID utilityTypeId;
    private String fiscalYear;
    private BigDecimal budgetedAmount;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
