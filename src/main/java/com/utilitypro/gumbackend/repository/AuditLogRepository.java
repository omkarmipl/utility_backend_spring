package com.utilitypro.gumbackend.repository;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
    
    /**
     * Find audit logs with filters and pagination
     * @param entityType Entity type filter (nullable)
     * @param changedBy User ID who made the change (nullable)
     * @param startDate Start date filter (nullable)
     * @param endDate End date filter (nullable)
     * @param pageable Pagination parameters
     * @return Page of audit logs
     */
    @Query("SELECT a FROM AuditLog a WHERE " +
           "(:entityType IS NULL OR a.entityType = :entityType) AND " +
           "(:changedBy IS NULL OR a.changedBy = :changedBy) AND " +
           "(:startDate IS NULL OR a.changedAt >= :startDate) AND " +
           "(:endDate IS NULL OR a.changedAt <= :endDate) " +
           "ORDER BY a.changedAt DESC")
    Page<AuditLog> findWithFilters(
        @Param("entityType") String entityType,
        @Param("changedBy") UUID changedBy,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        Pageable pageable
    );
    
    /**
     * Find audit logs by entity
     * @param entityType Entity type
     * @param entityId Entity ID
     * @return List of audit logs
     */
    java.util.List<AuditLog> findByEntityTypeAndEntityIdOrderByChangedAtDesc(String entityType, UUID entityId);
}
