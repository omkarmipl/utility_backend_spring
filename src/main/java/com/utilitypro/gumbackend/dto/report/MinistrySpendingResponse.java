package com.utilitypro.gumbackend.dto.report;
import java.util.List;
public record MinistrySpendingResponse(List<MinistrySpend> spending) {
    public record MinistrySpend(String ministryName, java.math.BigDecimal totalSpent) {}
}
