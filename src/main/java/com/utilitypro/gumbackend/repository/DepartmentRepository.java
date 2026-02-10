package com.utilitypro.gumbackend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utilitypro.gumbackend.domain.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    
    /**
     * Find departments by ministry
     * @param ministryId Ministry ID
     * @return List of departments
     */
    List<Department> findByMinistryId(UUID ministryId);
    
    /**
     * Find departments by ministry with pagination
     * @param ministryId Ministry ID
     * @param pageable Pagination parameters
     * @return Page of departments
     */
    Page<Department> findByMinistryId(UUID ministryId, Pageable pageable);
    
    /**
     * Find all active departments
     * @return List of active departments
     */
    List<Department> findByIsActiveTrue();
    
    /**
     * Find all active departments with pagination
     * @param pageable Pagination parameters
     * @return Page of active departments
     */
    Page<Department> findByIsActiveTrue(Pageable pageable);
    
    /**
     * Find departments by IDs (for scope filtering)
     * @param ids List of department IDs
     * @return List of departments
     */
    List<Department> findByIdIn(List<UUID> ids);
}
