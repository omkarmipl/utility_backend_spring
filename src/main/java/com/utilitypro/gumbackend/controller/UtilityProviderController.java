package com.utilitypro.gumbackend.controller;

import com.utilitypro.gumbackend.dto.provider.*;
import com.utilitypro.gumbackend.service.UtilityProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

/**
 * REST Controller for Utility Provider Management
 * Handles utility provider CRUD operations (e.g., LUCELEC, WASCO)
 */
@RestController
@RequestMapping("/api/utility-providers")
@RequiredArgsConstructor
public class UtilityProviderController {

    private final UtilityProviderService utilityProviderService;

    /**
     * GET /api/utility-providers
     * List utility providers (e.g., LUCELEC, WASCO)
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<com.utilitypro.gumbackend.dto.common.PageResponse<com.utilitypro.gumbackend.dto.masterdata.UtilityProviderResponse>> listUtilityProviders(
            @RequestParam(required = false) Boolean include_inactive,
            Pageable pageable) {
        var response = utilityProviderService.listUtilityProviders(include_inactive, pageable);
        return ResponseEntity.ok(response);
    }

    // TEMPORARILY DISABLED - DTO type mismatch
    // TODO: Fix service to accept CreateUtilityProviderRequest instead of UtilityProviderRequest
    /**
     * POST /api/utility-providers
     * Create new utility provider
     */
    /*
    @PostMapping
    @PreAuthorize("hasAnyRole('system_admin', 'mof_admin')")
    public ResponseEntity<UtilityProviderResponse> createUtilityProvider(@Valid @RequestBody CreateUtilityProviderRequest request) {
        UtilityProviderResponse response = utilityProviderService.createUtilityProvider(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    */

    // TEMPORARILY DISABLED - DTO type mismatch
    // TODO: Fix service to accept UpdateUtilityProviderRequest instead of UtilityProviderRequest
    /**
     * PUT /api/utility-providers/:id
     * Update utility provider
     */
    /*
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('system_admin', 'mof_admin')")
    public ResponseEntity<UtilityProviderResponse> updateUtilityProvider(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUtilityProviderRequest request) {
        UtilityProviderResponse response = utilityProviderService.updateUtilityProvider(id, request);
        return ResponseEntity.ok(response);
    }
    */


    /**
     * DELETE /api/utility-providers/:id
     * Delete utility provider
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('system_admin')")
    public ResponseEntity<Void> deleteUtilityProvider(@PathVariable UUID id) {
        utilityProviderService.deleteUtilityProvider(id);
        return ResponseEntity.noContent().build();
    }
}
