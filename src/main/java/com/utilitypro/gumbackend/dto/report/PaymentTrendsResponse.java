package com.utilitypro.gumbackend.dto.report;
import java.util.List;
public record PaymentTrendsResponse(List<TrendDataPoint> trends) {
    public record TrendDataPoint(String period, java.math.BigDecimal amount) {}
}
