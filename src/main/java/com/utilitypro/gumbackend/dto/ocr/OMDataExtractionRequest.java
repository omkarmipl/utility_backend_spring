package com.utilitypro.gumbackend.dto.ocr;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record OMDataExtractionRequest(@NotNull String ocrScanId, Map<String, String> fieldMappings) {}
