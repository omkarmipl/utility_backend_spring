package com.utilitypro.gumbackend.dto.document;

import java.time.OffsetDateTime;
import java.util.List;

public record DocumentListResponse(List<DocumentItem> documents) {
    public record DocumentItem(
            java.util.UUID id,
            String fileName,
            String fileType,
            Long fileSize,
            OffsetDateTime uploadedAt
    ) {}
}
