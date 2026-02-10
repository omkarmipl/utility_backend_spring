package com.utilitypro.gumbackend.dto.budget;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record BulkBudgetRequest(@NotNull List<CreateBudgetRequest> budgets) {}
