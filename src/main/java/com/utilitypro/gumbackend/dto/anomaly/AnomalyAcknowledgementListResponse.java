package com.utilitypro.gumbackend.dto.anomaly;

import java.util.List;

public record AnomalyAcknowledgementListResponse(List<AcknowledgementItem> acknowledgements) {
    public record AcknowledgementItem(java.util.UUID id, String anomalyId, String acknowledgedBy, String comments, java.time.OffsetDateTime acknowledgedAt) {}
}
