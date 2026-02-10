package com.utilitypro.gumbackend.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Alert setting response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertSettingResponse {
    private UUID id;
    private UUID ministryId;
    private UUID departmentId;
    private String alertType;
    private Boolean isEnabled;
    private String emailRecipients;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
