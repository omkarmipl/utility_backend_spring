package com.utilitypro.gumbackend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utilitypro.gumbackend.domain.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    
    /**
     * Find profile by user ID
     * @param userId User ID
     * @return Optional profile
     */
    Optional<Profile> findByUserId(UUID userId);
    
    /**
     * Delete profile by user ID
     * @param userId User ID
     */
    void deleteByUserId(UUID userId);
}
