package com.utilitypro.gumbackend.repository;

import com.utilitypro.gumbackend.domain.entity.UserScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserScopeRepository extends JpaRepository<UserScope, UUID> {
    
    /**
     * Find all scopes for a specific user
     * @param userId User ID
     * @return List of user scopes
     */
    List<UserScope> findByUserId(UUID userId);
    
    /**
     * Delete all scopes for a specific user
     * @param userId User ID
     */
    void deleteByUserId(UUID userId);
    
    /**
     * Find scopes by ministry
     * @param ministryId Ministry ID
     * @return List of user scopes
     */
    List<UserScope> findByMinistryId(UUID ministryId);
    
    /**
     * Find scopes by department
     * @param departmentId Department ID
     * @return List of user scopes
     */
    List<UserScope> findByDepartmentId(UUID departmentId);
}
