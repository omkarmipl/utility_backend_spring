package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_allocations", uniqueConstraints = @UniqueConstraint(name = "uk_payment_allocations_payment_bill", columnNames = {"payment_id", "bill_id"}))
@Getter
@Setter
@NoArgsConstructor
public class PaymentAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private UtilityBill bill;

    @Column(name = "allocated_amount", nullable = false)
    private BigDecimal allocatedAmount;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
