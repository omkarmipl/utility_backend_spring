package com.utilitypro.gumbackend.dto.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Update scopes request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateScopesRequest {
    
    @NotNull(message = "Scopes list is required")
    @Valid
    private List<ScopeDTO> scopes;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScopeDTO {
        
        @NotNull(message = "Ministry ID is required")
        private UUID ministryId;
        
        private UUID departmentId;
    }
}
