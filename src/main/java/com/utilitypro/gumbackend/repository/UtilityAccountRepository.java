package com.utilitypro.gumbackend.repository;

import com.utilitypro.gumbackend.domain.entity.UtilityAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilityAccountRepository extends JpaRepository<UtilityAccount, UUID> {
    
    List<UtilityAccount> findByLocationId(UUID locationId);
    
    List<UtilityAccount> findByIsActiveTrue();
    
    Optional<UtilityAccount> findByProviderAccountNumber(String providerAccountNumber);
}
