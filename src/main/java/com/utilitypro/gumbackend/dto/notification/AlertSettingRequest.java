package com.utilitypro.gumbackend.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Alert setting request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertSettingRequest {
    
    private UUID ministryId;
    private UUID departmentId;
    
    @NotBlank(message = "Alert type is required")
    private String alertType;
    
    @NotNull(message = "Is enabled flag is required")
    private Boolean isEnabled;
    
    private String emailRecipients;
}
