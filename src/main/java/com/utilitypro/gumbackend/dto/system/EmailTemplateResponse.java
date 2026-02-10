package com.utilitypro.gumbackend.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Email template response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateResponse {
    private UUID id;
    private String templateName;
    private String subject;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
