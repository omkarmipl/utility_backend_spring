package com.utilitypro.gumbackend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utilitypro.gumbackend.domain.entity.UtilityProvider;

@Repository
public interface UtilityProviderRepository extends JpaRepository<UtilityProvider, UUID> {
    
    /**
     * Find all active utility providers
     * @return List of active utility providers
     */
    List<UtilityProvider> findByIsActiveTrue();
    
    /**
     * Find all active utility providers with pagination
     * @param pageable Pagination parameters
     * @return Page of active utility providers
     */
    Page<UtilityProvider> findByIsActiveTrue(Pageable pageable);
}
