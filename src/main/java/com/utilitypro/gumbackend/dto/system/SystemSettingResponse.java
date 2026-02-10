package com.utilitypro.gumbackend.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * System setting response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemSettingResponse {
    private UUID id;
    private String settingKey;
    private String settingValue;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
