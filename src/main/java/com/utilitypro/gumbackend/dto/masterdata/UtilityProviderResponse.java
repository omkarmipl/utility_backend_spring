package com.utilitypro.gumbackend.dto.masterdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Utility Provider response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilityProviderResponse {
    private UUID id;
    private UUID utilityTypeId;
    private String name;
    private String contactInfo;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
