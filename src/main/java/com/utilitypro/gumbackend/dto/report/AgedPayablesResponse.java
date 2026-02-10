package com.utilitypro.gumbackend.dto.report;
import java.util.List;
public record AgedPayablesResponse(List<PayableItem> payables) {
    public record PayableItem(String category, java.math.BigDecimal amount, Integer daysOverdue) {}
}
