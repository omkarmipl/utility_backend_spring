package com.utilitypro.gumbackend.dto.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Role permission mapping request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionRequest {
    
    @NotBlank(message = "Role name is required")
    private String roleName;
    
    @NotNull(message = "Permission IDs list is required")
    @Valid
    private List<UUID> permissionIds;
}
