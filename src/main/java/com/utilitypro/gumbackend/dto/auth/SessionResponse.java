package com.utilitypro.gumbackend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Session response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponse {
    private UUID userId;
    private String email;
    private String fullName;
    private List<String> roles;
    private List<ScopeDTO> scopes;
    
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
