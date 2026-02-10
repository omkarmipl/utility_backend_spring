package com.utilitypro.gumbackend.dto.budget;

import java.math.BigDecimal;

public record UpdateBudgetRequest(
        BigDecimal allocatedAmount,
        BigDecimal spentAmount
) {}
