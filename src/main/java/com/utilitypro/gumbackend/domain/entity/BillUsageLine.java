package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "bill_usage_lines")
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

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UtilityBill getBill() { return bill; }
    public void setBill(UtilityBill bill) { this.bill = bill; }

    public String getMeterIdentifier() { return meterIdentifier; }
    public void setMeterIdentifier(String meterIdentifier) { this.meterIdentifier = meterIdentifier; }

    public String getTariffDescription() { return tariffDescription; }
    public void setTariffDescription(String tariffDescription) { this.tariffDescription = tariffDescription; }

    public BigDecimal getPrevReading() { return prevReading; }
    public void setPrevReading(BigDecimal prevReading) { this.prevReading = prevReading; }

    public BigDecimal getCurrReading() { return currReading; }
    public void setCurrReading(BigDecimal currReading) { this.currReading = currReading; }

    public BigDecimal getUsageQuantity() { return usageQuantity; }
    public void setUsageQuantity(BigDecimal usageQuantity) { this.usageQuantity = usageQuantity; }

    public BigDecimal getRatePerUnit() { return ratePerUnit; }
    public void setRatePerUnit(BigDecimal ratePerUnit) { this.ratePerUnit = ratePerUnit; }

    public BigDecimal getLineAmount() { return lineAmount; }
    public void setLineAmount(BigDecimal lineAmount) { this.lineAmount = lineAmount; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
