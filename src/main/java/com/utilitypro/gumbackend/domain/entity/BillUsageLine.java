package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "bill_usage_lines")
@Getter
@Setter
@NoArgsConstructor
public class BillUsageLine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private UtilityBill bill;

    @Column(name = "meter_identifier")
    private String meterIdentifier;

    @Column(name = "tariff_description")
    private String tariffDescription;

    @Column(name = "prev_reading")
    private BigDecimal prevReading;

    @Column(name = "curr_reading")
    private BigDecimal currReading;

    @Column(name = "usage_quantity")
    private BigDecimal usageQuantity;

    @Column(name = "rate_per_unit")
    private BigDecimal ratePerUnit;

    @Column(name = "line_amount", nullable = false)
    private BigDecimal lineAmount;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
