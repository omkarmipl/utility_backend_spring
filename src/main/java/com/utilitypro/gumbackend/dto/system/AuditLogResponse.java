package com.utilitypro.gumbackend.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Audit log response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {
    private UUID id;
    private String entityType;
    private UUID entityId;
    private String action;
    private UUID changedBy;
    private String changedByEmail;
    private LocalDateTime changedAt;
    private String changeDetails;
}
