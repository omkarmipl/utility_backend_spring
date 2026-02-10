package com.utilitypro.gumbackend.dto.budget;

import java.math.BigDecimal;
import java.util.List;

public record BudgetListResponse(List<BudgetItem> budgets) {
    public record BudgetItem(
            java.util.UUID id,
            String budgetName,
            BigDecimal allocatedAmount,
            BigDecimal spentAmount,
            String fiscalYear
    ) {}
}
