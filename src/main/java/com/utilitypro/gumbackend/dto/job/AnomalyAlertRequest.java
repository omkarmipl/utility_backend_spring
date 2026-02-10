package com.utilitypro.gumbackend.dto.job;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AnomalyAlertRequest(
        @NotNull java.util.UUID anomalyId,
        List<String> recipientEmails
) {}
