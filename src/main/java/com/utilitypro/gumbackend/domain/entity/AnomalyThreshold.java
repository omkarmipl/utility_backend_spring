package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "anomaly_thresholds")
@Getter
@Setter
@NoArgsConstructor
public class AnomalyThreshold {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ministry_id")
    private Ministry ministry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utility_type_id")
    private UtilityType utilityType;

    @Column(name = "threshold_multiplier", nullable = false)
    private BigDecimal thresholdMultiplier;

    @Column(name = "max_deviation_percent")
    private BigDecimal maxDeviationPercent;

    @Column(name = "min_amount_threshold")
    private BigDecimal minAmountThreshold;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
