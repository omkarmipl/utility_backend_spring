package com.utilitypro.gumbackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utilitypro.gumbackend.domain.entity.Ministry;

@Repository
public interface MinistryRepository extends JpaRepository<Ministry, UUID> {
    
    /**
     * Find all active ministries
     * @return List of active ministries
     */
    List<Ministry> findByIsActiveTrue();
    
    /**
     * Find ministry by code
     * @param code Ministry code
     * @return Optional ministry
     */
    Optional<Ministry> findByCode(String code);
    
    /**
     * Find ministries by IDs (for scope filtering)
     * @param ids List of ministry IDs
     * @return List of ministries
     */
    List<Ministry> findByIdIn(List<UUID> ids);
}
