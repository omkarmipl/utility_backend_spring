package com.utilitypro.gumbackend.dto.budget;

public record BulkBudgetResponse(Integer processedCount, Integer successCount, Integer failureCount) {}
