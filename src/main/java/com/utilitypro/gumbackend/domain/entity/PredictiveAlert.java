package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "predictive_alerts")
@Getter
@Setter
@NoArgsConstructor
public class PredictiveAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private UtilityAccount account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ministry_id")
    private Ministry ministry;

    @Column(name = "predicted_amount", nullable = false)
    private BigDecimal predictedAmount;

    @Column(name = "predicted_due_date", nullable = false)
    private LocalDate predictedDueDate;

    @Column(name = "historical_average", nullable = false)
    private BigDecimal historicalAverage;

    @Column(name = "trend_direction", nullable = false, columnDefinition = "text")
    private String trendDirection;

    @Column(name = "confidence_level", nullable = false)
    private BigDecimal confidenceLevel;

    @Column(name = "alert_status", nullable = false, columnDefinition = "text")
    private String alertStatus;

    @Column(name = "alert_message", columnDefinition = "text")
    private String alertMessage;

    @Column(name = "acknowledged_by")
    private UUID acknowledgedBy;

    @Column(name = "acknowledged_at")
    private OffsetDateTime acknowledgedAt;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
