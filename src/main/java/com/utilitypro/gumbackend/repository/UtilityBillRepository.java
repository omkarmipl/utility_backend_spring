package com.utilitypro.gumbackend.repository;

import com.utilitypro.gumbackend.domain.entity.UtilityBill;
import com.utilitypro.gumbackend.domain.enums.BillStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UtilityBillRepository extends JpaRepository<UtilityBill, UUID> {
    

    @Query("SELECT b FROM UtilityBill b WHERE " +
           "(:ministryId IS NULL OR b.ministryId = :ministryId) AND " +
           "(:status IS NULL OR b.status = :status)")
    Page<UtilityBill> findWithFilters(
        @Param("ministryId") UUID ministryId,
        @Param("status") BillStatus status,
        Pageable pageable
    );
    
    @Query("SELECT b FROM UtilityBill b WHERE b.paidFlag = false OR b.paidFlag IS NULL")
    List<UtilityBill> findUnpaidBills();
    
    List<UtilityBill> findByUtilityAccountId(UUID utilityAccountId);
}
