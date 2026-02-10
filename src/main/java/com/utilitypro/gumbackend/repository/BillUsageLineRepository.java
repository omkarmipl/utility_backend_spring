package com.utilitypro.gumbackend.repository;

import com.utilitypro.gumbackend.domain.entity.BillUsageLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BillUsageLineRepository extends JpaRepository<BillUsageLine, UUID> {
    
    List<BillUsageLine> findByBillId(UUID billId);
    
    void deleteByBillId(UUID billId);
}
