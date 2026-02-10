package com.utilitypro.gumbackend.dto.masterdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Location response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    private UUID id;
    private UUID ministryId;
    private UUID departmentId;
    private String name;
    private String address;
    private String city;
    private String district;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
