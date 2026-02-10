package com.utilitypro.gumbackend.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Anomaly threshold response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnomalyThresholdResponse {
    private UUID id;
    private UUID utilityTypeId;
    private UUID ministryId;
    private String thresholdType;
    private BigDecimal thresholdValue;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
