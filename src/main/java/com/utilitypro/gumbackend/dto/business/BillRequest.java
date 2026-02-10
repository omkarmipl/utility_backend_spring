package com.utilitypro.gumbackend.dto.business;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Bill request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {
    
    @NotNull(message = "Account ID is required")
    private UUID accountId;
    
    @NotBlank(message = "Invoice number is required")
    @Size(max = 100, message = "Invoice number must be less than 100 characters")
    private String invoiceNumber;
    
    @NotNull(message = "Bill date is required")
    @PastOrPresent(message = "Bill date cannot be in the future")
    private LocalDate billDate;
    
    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
    
    @NotNull(message = "Billing period start is required")
    private LocalDate billingPeriodStart;
    
    @NotNull(message = "Billing period end is required")
    private LocalDate billingPeriodEnd;
    
    @NotNull(message = "Subtotal is required")
    @DecimalMin(value = "0.0", message = "Subtotal must be positive")
    private BigDecimal subtotal;
    
    @DecimalMin(value = "0.0", message = "Tax must be positive")
    private BigDecimal tax;
    
    @DecimalMin(value = "0.0", message = "Other charges must be positive")
    private BigDecimal otherCharges;
    
    private String notes;
    
    private UUID seasonId;
}
