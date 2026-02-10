package com.utilitypro.gumbackend.dto.notification;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Anomaly threshold request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnomalyThresholdRequest {
    
    private UUID utilityTypeId;
    private UUID ministryId;
    
    @NotBlank(message = "Threshold type is required")
    @Pattern(regexp = "percentage_increase|absolute_amount|usage_spike", 
             message = "Threshold type must be one of: percentage_increase, absolute_amount, usage_spike")
    private String thresholdType;
    
    @NotNull(message = "Threshold value is required")
    @DecimalMin(value = "0.0", message = "Threshold value must be positive")
    private BigDecimal thresholdValue;
    
    private Boolean isActive;
}
