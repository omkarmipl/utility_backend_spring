package com.utilitypro.gumbackend.dto.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Utility Account response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilityAccountResponse {
    private UUID id;
    private UUID providerId;
    private UUID ministryId;
    private UUID departmentId;
    private UUID locationId;
    private String providerAccountNumber;
    private LocalDate accountStartDate;
    private LocalDate accountEndDate;
    private String accountStatus;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
