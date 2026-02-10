package com.utilitypro.gumbackend.repository;

import com.utilitypro.gumbackend.domain.entity.UtilityBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilityBudgetRepository extends JpaRepository<UtilityBudget, UUID> {
    
    List<UtilityBudget> findByFiscalYear(Integer fiscalYear);
    
    Optional<UtilityBudget> findByMinistryIdAndDepartmentIdAndUtilityTypeIdAndFiscalYear(
        UUID ministryId, UUID departmentId, UUID utilityTypeId, Integer fiscalYear
    );
}
