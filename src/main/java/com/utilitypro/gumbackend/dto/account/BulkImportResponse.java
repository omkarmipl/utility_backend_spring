package com.utilitypro.gumbackend.dto.account;
public record BulkImportResponse(Integer successCount, Integer failureCount, java.util.List<String> errors) {}
