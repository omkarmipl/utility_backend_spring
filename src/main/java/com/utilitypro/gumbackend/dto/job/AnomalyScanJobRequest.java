package com.utilitypro.gumbackend.dto.job;

import jakarta.validation.constraints.NotNull;

public record AnomalyScanJobRequest(
        @NotNull java.util.UUID ministryId,
        java.time.OffsetDateTime startDate,
        java.time.OffsetDateTime endDate
) {}
