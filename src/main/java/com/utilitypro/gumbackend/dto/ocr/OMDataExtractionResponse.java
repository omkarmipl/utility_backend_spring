package com.utilitypro.gumbackend.dto.ocr;

import java.util.Map;

public record OMDataExtractionResponse(java.util.UUID billId, Map<String, Object> extractedFields) {}
