package com.utilitypro.gumbackend.dto.ocr;

import jakarta.validation.constraints.NotNull;

public record OCRScanRequest(@NotNull String documentUrl, String documentType) {}
