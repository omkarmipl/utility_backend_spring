package com.utilitypro.gumbackend.dto.masterdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Utility Type response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilityTypeResponse {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
