package com.utilitypro.gumbackend.dto.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Bill response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {
    private UUID id;
    private UUID accountId;
    private String invoiceNumber;
    private LocalDate billDate;
    private LocalDate dueDate;
    private LocalDate billingPeriodStart;
    private LocalDate billingPeriodEnd;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal otherCharges;
    private BigDecimal totalAmount;
    private String status;
    private Boolean paidFlag;
    private String notes;
    private UUID seasonId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
