package com.utilitypro.gumbackend.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Document upload response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadResponse {
    private String documentUrl;
    private String fileName;
    private Long fileSize;
    private String contentType;
}
