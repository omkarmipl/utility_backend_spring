package com.utilitypro.gumbackend.dto.ocr;

import java.util.Map;

public record OCRScanResponse(String scanId, String status, Map<String, Object> extractedData) {}
