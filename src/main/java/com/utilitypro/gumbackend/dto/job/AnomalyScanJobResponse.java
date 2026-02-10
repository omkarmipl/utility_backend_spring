package com.utilitypro.gumbackend.dto.job;

public record AnomalyScanJobResponse(String jobId, String status, Integer anomaliesFound) {}
