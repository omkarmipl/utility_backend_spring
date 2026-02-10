package com.utilitypro.gumbackend.dto.anomaly;

import java.util.List;

public record AcknowledgementListResponse(List<AcknowledgementItem> acknowledgements) {
    public record AcknowledgementItem(
            java.util.UUID id,
            java.util.UUID billId,
            String notes,
            java.time.OffsetDateTime acknowledgedAt
    ) {}
}
