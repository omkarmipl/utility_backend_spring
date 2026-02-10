package com.utilitypro.gumbackend.dto.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Update roles request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRolesRequest {
    
    @NotNull(message = "Roles list is required")
    @Valid
    private List<String> roles;
}
