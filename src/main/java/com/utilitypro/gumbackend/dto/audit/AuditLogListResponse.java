package com.utilitypro.gumbackend.dto.audit;

import java.time.OffsetDateTime;
import java.util.List;

public record AuditLogListResponse(List<AuditLogItem> logs) {
    public record AuditLogItem(
            java.util.UUID id,
            String entityType,
            java.util.UUID entityId,
            String action,
            OffsetDateTime changedAt,
            java.util.UUID changedBy
    ) {}
}
