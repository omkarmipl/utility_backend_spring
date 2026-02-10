package com.utilitypro.gumbackend.dto.bill;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateBillUsageLineRequest(
        @NotNull String meterIdentifier,
        @NotNull String tariffDescription,
        @NotNull BigDecimal prevReading,
        @NotNull BigDecimal currReading,
        @NotNull BigDecimal usageQuantity,
        @NotNull BigDecimal ratePerUnit,
        @NotNull BigDecimal lineAmount
) {}
