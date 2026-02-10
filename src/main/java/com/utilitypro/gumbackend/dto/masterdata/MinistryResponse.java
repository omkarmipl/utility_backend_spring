package com.utilitypro.gumbackend.dto.masterdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Ministry response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinistryResponse {
    private UUID id;
    private String name;
    private String code;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
