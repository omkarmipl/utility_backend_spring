package com.utilitypro.gumbackend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utilitypro.gumbackend.domain.entity.UtilityType;

@Repository
public interface UtilityTypeRepository extends JpaRepository<UtilityType, UUID> {
    
    /**
     * Find all active utility types
     * @return List of active utility types
     */
    List<UtilityType> findByIsActiveTrue();
    
    /**
     * Find all active utility types with pagination
     * @param pageable Pagination parameters
     * @return Page of active utility types
     */
    Page<UtilityType> findByIsActiveTrue(Pageable pageable);
}
