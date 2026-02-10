package com.utilitypro.gumbackend.dto.business;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Payment request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    
    @NotNull(message = "Provider ID is required")
    private UUID providerId;
    
    @NotNull(message = "Ministry ID is required")
    private UUID ministryId;
    
    private UUID departmentId;
    
    @NotBlank(message = "Payment reference is required")
    @Size(max = 100, message = "Payment reference must be less than 100 characters")
    private String paymentReference;
    
    @NotNull(message = "Payment date is required")
    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDate paymentDate;
    
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", message = "Total amount must be greater than zero")
    private BigDecimal totalAmount;
    
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    
    private String notes;
    
    @Valid
    private List<PaymentAllocationDTO> allocations;
    
    /**
     * Nested DTO for payment allocations
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentAllocationDTO {
        
        @NotNull(message = "Bill ID is required")
        private UUID billId;
        
        @NotNull(message = "Allocated amount is required")
        @DecimalMin(value = "0.01", message = "Allocated amount must be greater than zero")
        private BigDecimal allocatedAmount;
    }
}
