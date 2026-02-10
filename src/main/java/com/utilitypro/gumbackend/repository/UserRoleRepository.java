package com.utilitypro.gumbackend.repository;

import com.utilitypro.gumbackend.domain.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    
    /**
     * Find all roles for a specific user
     * @param userId User ID
     * @return List of user roles
     */
    List<UserRole> findByUserId(UUID userId);
    
    /**
     * Delete all roles for a specific user
     * @param userId User ID
     */
    void deleteByUserId(UUID userId);
    
    /**
     * Find users by role
     * @param role Role
     * @return List of user roles
     */
    List<UserRole> findByRole(com.utilitypro.gumbackend.domain.enums.GumRole role);
}
