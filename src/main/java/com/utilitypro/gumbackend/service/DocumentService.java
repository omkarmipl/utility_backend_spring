package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Document Service
 * Handles document uploads and storage
 */
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final AuthorizationHelper authorizationHelper;
    
    private static final String UPLOAD_DIR = "uploads/documents/";

    @Transactional
    public com.utilitypro.gumbackend.dto.document.DocumentUploadResponse uploadDocument(MultipartFile file, String entityType, UUID entityId) {
        try {
            authorizationHelper.requireRole("system_admin", "mof_admin", "ministry_admin", "department_user");

            // Validate file
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".") 
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";
            String filename = UUID.randomUUID().toString() + extension;

            // Create upload directory if not exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save file
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            // Return response
            return new com.utilitypro.gumbackend.dto.document.DocumentUploadResponse(UUID.randomUUID(), "/documents/" + filename, originalFilename);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}
