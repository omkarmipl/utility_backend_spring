package com.utilitypro.gumbackend.controller;

import com.utilitypro.gumbackend.dto.department.*;
import com.utilitypro.gumbackend.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

/**
 * REST Controller for Department Management
 * Handles department CRUD operations
 */
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * GET /api/departments
     * List departments by ministry
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DepartmentListResponse> listDepartments(
            @RequestParam(required = false) UUID ministry_id) {
        DepartmentListResponse response = departmentService.listDepartments(ministry_id);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/departments
     * Create new department
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('system_admin', 'mof_admin', 'ministry_admin')")
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {
        DepartmentResponse response = departmentService.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * PUT /api/departments/:id
     * Update department
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('system_admin', 'mof_admin', 'ministry_admin')")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDepartmentRequest request) {
        DepartmentResponse response = departmentService.updateDepartment(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/departments/:id
     * Delete department
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('system_admin', 'mof_admin')")
    public ResponseEntity<Void> deleteDepartment(@PathVariable UUID id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
