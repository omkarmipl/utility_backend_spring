package com.utilitypro.gumbackend.dto.budget;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateBudgetRequest(
        @NotNull String budgetName,
        @NotNull java.util.UUID departmentId,
        @NotNull BigDecimal allocatedAmount,
        @NotNull String fiscalYear
) {}
