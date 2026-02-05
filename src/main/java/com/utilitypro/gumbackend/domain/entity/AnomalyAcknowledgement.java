package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "anomaly_acknowledgements")
@Getter
@Setter
@NoArgsConstructor
public class AnomalyAcknowledgement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private UtilityBill bill;

    @Column(name = "acknowledged_by", nullable = false)
    private UUID acknowledgedBy;

    @Column(columnDefinition = "text")
    private String notes;

    @Column(name = "acknowledged_at", nullable = false)
    private OffsetDateTime acknowledgedAt;
}
