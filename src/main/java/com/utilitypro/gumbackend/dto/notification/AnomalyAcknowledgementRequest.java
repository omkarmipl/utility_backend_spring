package com.utilitypro.gumbackend.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Anomaly acknowledgement request DTO
 */
public record AnomalyAcknowledgementRequest(
        
        @NotNull(message = "Bill ID is required")
        UUID billId,
        
        @NotBlank(message = "Anomaly type is required")
        String anomalyType,
        
        @NotBlank(message = "Action taken is required")
        String actionTaken,
        
        String notes
) {}
