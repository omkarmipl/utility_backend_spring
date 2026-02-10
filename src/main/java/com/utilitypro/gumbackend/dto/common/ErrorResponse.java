package com.utilitypro.gumbackend.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standard error response format
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;
    private Map<String, String> errors; // Field-level validation errors
    
    /**
     * Create simple error response
     */
    public static ErrorResponse of(String message, int status, String path) {
        return ErrorResponse.builder()
                .message(message)
                .status(status)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }
    
    /**
     * Create error response with validation errors
     */
    public static ErrorResponse of(String message, int status, String path, Map<String, String> errors) {
        return ErrorResponse.builder()
                .message(message)
                .status(status)
                .timestamp(LocalDateTime.now())
                .path(path)
                .errors(errors)
                .build();
    }
}
