package com.utilitypro.gumbackend.controller;

import com.utilitypro.gumbackend.dto.bill.*;
import com.utilitypro.gumbackend.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

/**
 * REST Controller for Utility Bill Management
 * Handles bill CRUD operations, status updates, and unpaid bill queries
 */
@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    /**
     * GET /api/bills
     * List utility bills with filters
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BillListResponse> listBills(
            @RequestParam(required = false) UUID ministry_id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        BillListResponse response = billService.listBills(ministry_id, status, page, limit);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/bills/:id
     * Get bill details with usage lines
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BillDetailResponse> getBill(@PathVariable UUID id) {
        BillDetailResponse response = billService.getBill(id);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/bills
     * Create new bill with usage lines
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('system_admin', 'mof_admin', 'ministry_admin', 'department_user')")
    public ResponseEntity<BillResponse> createBill(@Valid @RequestBody CreateBillRequest request) {
        BillResponse response = billService.createBill(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * PUT /api/bills/:id
     * Update bill details
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('system_admin', 'mof_admin', 'ministry_admin', 'department_user')")
    public ResponseEntity<BillResponse> updateBill(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateBillRequest request) {
        BillResponse response = billService.updateBill(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/bills/:id/status
     * Update bill status (submit, approve, mark as paid)
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('system_admin', 'mof_admin', 'ministry_admin')")
    public ResponseEntity<BillStatusResponse> updateBillStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateBillStatusRequest request) {
        BillStatusResponse response = billService.updateBillStatus(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/bills/unpaid
     * List unpaid/partially paid bills
     */
    @GetMapping("/unpaid")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UnpaidBillListResponse> listUnpaidBills(
            @RequestParam(required = false) UUID provider_id) {
        UnpaidBillListResponse response = billService.listUnpaidBills(provider_id);
        return ResponseEntity.ok(response);
    }
}
