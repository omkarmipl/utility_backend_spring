package com.utilitypro.gumbackend.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * OCR result response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OCRResultResponse {
    private String invoiceNumber;
    private LocalDate billDate;
    private LocalDate dueDate;
    private BigDecimal totalAmount;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private String providerName;
    private String accountNumber;
    private Double confidence;
    private Map<String, Object> rawData;
}
