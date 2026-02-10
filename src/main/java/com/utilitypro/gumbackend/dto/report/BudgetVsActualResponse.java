package com.utilitypro.gumbackend.dto.report;

import java.math.BigDecimal;
import java.util.List;

public record BudgetVsActualResponse(List<BudgetComparison> comparisons) {
    public record BudgetComparison(
            String category, // Department or Ministry name
            BigDecimal budgetAmount,
            BigDecimal actualSpent,
            BigDecimal variance,
            Double percentageUsed
    ) {}
}
