package com.utilitypro.gumbackend.domain.entity;

import com.utilitypro.gumbackend.domain.enums.BillStatus;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "utility_bills")
@NoArgsConstructor
public class UtilityBill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private UtilityAccount account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ministry_id")
    private Ministry ministry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Column(name = "billing_period_from", nullable = false)
    private LocalDate billingPeriodFrom;

    @Column(name = "billing_period_to", nullable = false)
    private LocalDate billingPeriodTo;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "subtotal_amount", nullable = false)
    private BigDecimal subtotalAmount;

    @Column(name = "tax_amount")
    private BigDecimal taxAmount;

    @Column(name = "other_charges")
    private BigDecimal otherCharges;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "currency_code")
    private String currencyCode;

    @Enumerated(EnumType.STRING)
    private BillStatus status;

    @Column(name = "paid_flag")
    private Boolean paidFlag;

    @Column(name = "scanned_invoice_url", columnDefinition = "text")
    private String scannedInvoiceUrl;

    @Column(columnDefinition = "text")
    private String notes;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "approved_by")
    private UUID approvedBy;

    @Column(name = "approved_at")
    private OffsetDateTime approvedAt;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UtilityAccount getAccount() { return account; }
    public void setAccount(UtilityAccount account) { this.account = account; }

    public Ministry getMinistry() { return ministry; }
    public void setMinistry(Ministry ministry) { this.ministry = ministry; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public LocalDate getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }

    public LocalDate getBillingPeriodFrom() { return billingPeriodFrom; }
    public void setBillingPeriodFrom(LocalDate billingPeriodFrom) { this.billingPeriodFrom = billingPeriodFrom; }

    public LocalDate getBillingPeriodTo() { return billingPeriodTo; }
    public void setBillingPeriodTo(LocalDate billingPeriodTo) { this.billingPeriodTo = billingPeriodTo; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public BigDecimal getSubtotalAmount() { return subtotalAmount; }
    public void setSubtotalAmount(BigDecimal subtotalAmount) { this.subtotalAmount = subtotalAmount; }

    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }

    public BigDecimal getOtherCharges() { return otherCharges; }
    public void setOtherCharges(BigDecimal otherCharges) { this.otherCharges = otherCharges; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getCurrencyCode() { return currencyCode; }
    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }

    public BillStatus getStatus() { return status; }
    public void setStatus(BillStatus status) { this.status = status; }

    public Boolean getPaidFlag() { return paidFlag; }
    public void setPaidFlag(Boolean paidFlag) { this.paidFlag = paidFlag; }

    public String getScannedInvoiceUrl() { return scannedInvoiceUrl; }
    public void setScannedInvoiceUrl(String scannedInvoiceUrl) { this.scannedInvoiceUrl = scannedInvoiceUrl; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }

    public UUID getApprovedBy() { return approvedBy; }
    public void setApprovedBy(UUID approvedBy) { this.approvedBy = approvedBy; }

    public OffsetDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(OffsetDateTime approvedAt) { this.approvedAt = approvedAt; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
