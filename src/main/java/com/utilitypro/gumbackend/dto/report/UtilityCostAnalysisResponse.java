package com.utilitypro.gumbackend.dto.report;
import java.util.List;
public record UtilityCostAnalysisResponse(List<CostAnalysis> analysis) {
    public record CostAnalysis(String utilityType, java.math.BigDecimal totalCost, java.math.BigDecimal averageCost) {}
}
