package com.utilitypro.gumbackend.repository;

import com.utilitypro.gumbackend.domain.entity.AnomalyAcknowledgement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnomalyAcknowledgementRepository extends JpaRepository<AnomalyAcknowledgement, UUID> {
    
    List<AnomalyAcknowledgement> findByBillId(UUID billId);
}
