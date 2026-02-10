package com.utilitypro.gumbackend.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * User detail response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {
    private UUID id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String jobTitle;
    private String department;
    private List<String> roles;
    private List<ScopeDTO> scopes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScopeDTO {
        private UUID ministryId;
        private String ministryName;
        private UUID departmentId;
        private String departmentName;
    }
}
