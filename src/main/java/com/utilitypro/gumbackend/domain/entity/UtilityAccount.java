package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "utility_accounts", uniqueConstraints = @UniqueConstraint(name = "uk_utility_accounts_provider_account", columnNames = {"provider_id", "provider_account_no"}))
@Getter
@Setter
@NoArgsConstructor
public class UtilityAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private UtilityProvider provider;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "utility_type_id", nullable = false)
    private UtilityType utilityType;

    @Column(name = "provider_account_no", nullable = false)
    private String providerAccountNo;

    @Column(name = "meter_number")
    private String meterNumber;

    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_watchlisted", nullable = false)
    private Boolean isWatchlisted;

    @Column(name = "watchlist_reason")
    private String watchlistReason;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
