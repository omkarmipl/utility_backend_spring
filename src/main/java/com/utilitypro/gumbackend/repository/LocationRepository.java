package com.utilitypro.gumbackend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utilitypro.gumbackend.domain.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    
    /**
     * Find locations by ministry
     * @param ministryId Ministry ID
     * @return List of locations
     */
    List<Location> findByMinistryId(UUID ministryId);
    
    /**
     * Find locations by ministry with pagination
     * @param ministryId Ministry ID
     * @param pageable Pagination parameters
     * @return Page of locations
     */
    Page<Location> findByMinistryId(UUID ministryId, Pageable pageable);
    
    /**
     * Find locations by department
     * @param departmentId Department ID
     * @return List of locations
     */
    List<Location> findByDepartmentId(UUID departmentId);
    
    /**
     * Find locations by department with pagination
     * @param departmentId Department ID
     * @param pageable Pagination parameters
     * @return Page of locations
     */
    Page<Location> findByDepartmentId(UUID departmentId, Pageable pageable);
    
    /**
     * Find all active locations
     * @return List of active locations
     */
    List<Location> findByIsActiveTrue();
    
    /**
     * Find all active locations with pagination
     * @param pageable Pagination parameters
     * @return Page of active locations
     */
    Page<Location> findByIsActiveTrue(Pageable pageable);
}
