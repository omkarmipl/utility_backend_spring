package com.utilitypro.gumbackend.controller;

import com.utilitypro.gumbackend.dto.document.DocumentUploadResponse;
import com.utilitypro.gumbackend.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * REST Controller for Document Upload and Storage
 * Handles document uploads (invoice scans, etc.)
 */
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    /**
     * POST /api/documents/upload
     * Upload document (invoice scans, etc.)
     */
    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DocumentUploadResponse> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("entity_type") String entityType,
            @RequestParam("entity_id") UUID entityId) {
        DocumentUploadResponse response = documentService.uploadDocument(file, entityType, entityId);
        return ResponseEntity.ok(response);
    }
}
