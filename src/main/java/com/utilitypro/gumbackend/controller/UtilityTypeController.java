package com.utilitypro.gumbackend.controller;

import com.utilitypro.gumbackend.dto.utilitytype.*;
import com.utilitypro.gumbackend.service.UtilityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

/**
 * REST Controller for Utility Type Management
 * Handles utility type CRUD operations (electricity, water, etc.)
 */
@RestController
@RequestMapping("/api/utility-types")
@RequiredArgsConstructor
public class UtilityTypeController {

    private final UtilityTypeService utilityTypeService;

    /**
     * GET /api/utility-types
     * List utility types (electricity, water, etc.)
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<com.utilitypro.gumbackend.dto.common.PageResponse<com.utilitypro.gumbackend.dto.masterdata.UtilityTypeResponse>> listUtilityTypes(
            @RequestParam(required = false) Boolean include_inactive,
            Pageable pageable) {
        var response = utilityTypeService.listUtilityTypes(include_inactive, pageable);
        return ResponseEntity.ok(response);
    }

    // TEMPORARILY DISABLED - DTO type mismatch
    // TODO: Fix service to accept CreateUtilityTypeRequest instead of UtilityTypeRequest
    /**
     * POST /api/utility-types
     * Create new utility type
     */
    /*
    @PostMapping
    @PreAuthorize("hasRole('system_admin')")
    public ResponseEntity<UtilityTypeResponse> createUtilityType(@Valid @RequestBody CreateUtilityTypeRequest request) {
        UtilityTypeResponse response = utilityTypeService.createUtilityType(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    */

    // TEMPORARILY DISABLED - DTO type mismatch
    // TODO: Fix service to accept UpdateUtilityTypeRequest instead of UtilityTypeRequest
    /**
     * PUT /api/utility-types/:id
     * Update utility type
     */
    /*
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('system_admin')")
    public ResponseEntity<UtilityTypeResponse> updateUtilityType(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUtilityTypeRequest request) {
        UtilityTypeResponse response = utilityTypeService.updateUtilityType(id, request);
        return ResponseEntity.ok(response);
    }
    */


    /**
     * DELETE /api/utility-types/:id
     * Delete utility type
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('system_admin')")
    public ResponseEntity<Void> deleteUtilityType(@PathVariable UUID id) {
        utilityTypeService.deleteUtilityType(id);
        return ResponseEntity.noContent().build();
    }
}
