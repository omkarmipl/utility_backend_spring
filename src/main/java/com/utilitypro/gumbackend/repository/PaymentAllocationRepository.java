package com.utilitypro.gumbackend.repository;

import com.utilitypro.gumbackend.domain.entity.PaymentAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentAllocationRepository extends JpaRepository<PaymentAllocation, UUID> {
    
    List<PaymentAllocation> findByPaymentId(UUID paymentId);
    
    List<PaymentAllocation> findByBillId(UUID billId);
    
    void deleteByPaymentId(UUID paymentId);
    
    @Query("SELECT COALESCE(SUM(pa.allocatedAmount), 0) FROM PaymentAllocation pa WHERE pa.billId = :billId")
    BigDecimal sumAllocatedAmountByBillId(@Param("billId") UUID billId);
}
