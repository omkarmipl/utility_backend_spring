package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "utility_accounts", uniqueConstraints = @UniqueConstraint(name = "uk_utility_accounts_provider_account", columnNames = {"provider_id", "provider_account_no"}))
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

    @Column(name = "account_name")
    private String accountName;

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

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
        if (isActive == null) {
            isActive = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public UtilityProvider getProvider() { return provider; }
    public void setProvider(UtilityProvider provider) { this.provider = provider; }

    public UtilityType getUtilityType() { return utilityType; }
    public void setUtilityType(UtilityType utilityType) { this.utilityType = utilityType; }

    public String getProviderAccountNo() { return providerAccountNo; }
    public void setProviderAccountNo(String providerAccountNo) { this.providerAccountNo = providerAccountNo; }

    // Alias methods for backwards compatibility
    public String getProviderAccountNumber() { return providerAccountNo; }
    public void setProviderAccountNumber(String providerAccountNumber) { this.providerAccountNo = providerAccountNumber; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getMeterNumber() { return meterNumber; }
    public void setMeterNumber(String meterNumber) { this.meterNumber = meterNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Boolean getIsWatchlisted() { return isWatchlisted; }
    public void setIsWatchlisted(Boolean isWatchlisted) { this.isWatchlisted = isWatchlisted; }

    public String getWatchlistReason() { return watchlistReason; }
    public void setWatchlistReason(String watchlistReason) { this.watchlistReason = watchlistReason; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
