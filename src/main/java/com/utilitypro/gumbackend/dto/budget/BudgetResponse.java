package com.utilitypro.gumbackend.dto.budget;

public record BudgetResponse(
        java.util.UUID id,
        String budgetName,
        java.math.BigDecimal allocatedAmount,
        java.math.BigDecimal spentAmount
) {}
