package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.domain.entity.UtilityBudget;
import com.utilitypro.gumbackend.repository.AuditLogRepository;
import com.utilitypro.gumbackend.repository.UtilityBudgetRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Budget Service
 * Handles budget CRUD and bulk operations
 */
@Service
@RequiredArgsConstructor
public class BudgetService {

    private final UtilityBudgetRepository utilityBudgetRepository;
    private final AuditLogRepository auditLogRepository;
    private final AuthorizationHelper authorizationHelper;

    @Transactional(readOnly = true)
    public List<UtilityBudget> listBudgets(String fiscalYear) {
        if (fiscalYear != null) {
            // Convert String to Integer for fiscal year
            return utilityBudgetRepository.findByFiscalYear(Integer.parseInt(fiscalYear));
        }
        return utilityBudgetRepository.findAll();
    }

    @Transactional
    public UtilityBudget createBudget(UtilityBudget budget) {
        authorizationHelper.requireRole("system_admin", "mof_admin", "ministry_admin");

        // Validate unique constraint - access IDs from relationships
        UUID ministryId = budget.getMinistry() != null ? budget.getMinistry().getId() : null;
        UUID departmentId = budget.getDepartment() != null ? budget.getDepartment().getId() : null;
        UUID utilityTypeId = budget.getUtilityType() != null ? budget.getUtilityType().getId() : null;
        
        if (utilityBudgetRepository.findByMinistryIdAndDepartmentIdAndUtilityTypeIdAndFiscalYear(
                ministryId, departmentId, 
                utilityTypeId, budget.getFiscalYear()).isPresent()) {
            throw new IllegalArgumentException("Budget already exists for this combination");
        }

        UtilityBudget saved = utilityBudgetRepository.save(budget);
        createAuditLog("UtilityBudget", saved.getId(), "CREATE");
        return saved;
    }

    @Transactional
    public List<UtilityBudget> bulkCreateBudgets(List<UtilityBudget> budgets) {
        authorizationHelper.requireRole("system_admin", "mof_admin", "ministry_admin");

        // Validate all budgets before inserting
        for (UtilityBudget budget : budgets) {
            UUID ministryId = budget.getMinistry() != null ? budget.getMinistry().getId() : null;
            UUID departmentId = budget.getDepartment() != null ? budget.getDepartment().getId() : null;
            UUID utilityTypeId = budget.getUtilityType() != null ? budget.getUtilityType().getId() : null;
            
            if (utilityBudgetRepository.findByMinistryIdAndDepartmentIdAndUtilityTypeIdAndFiscalYear(
                    ministryId, departmentId,
                    utilityTypeId, budget.getFiscalYear()).isPresent()) {
                throw new IllegalArgumentException("Duplicate budget found");
            }
        }

        List<UtilityBudget> saved = utilityBudgetRepository.saveAll(budgets);
        createAuditLog("UtilityBudget", null, "BULK_CREATE");
        return saved;
    }

    @Transactional
    public UtilityBudget updateBudget(UUID id, UtilityBudget updatedBudget) {
        authorizationHelper.requireRole("system_admin", "mof_admin", "ministry_admin");

        UtilityBudget existing = utilityBudgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found"));

        // Use monthlyBudgetAmt instead of budgetedAmount
        existing.setMonthlyBudgetAmt(updatedBudget.getMonthlyBudgetAmt());
        
        UtilityBudget saved = utilityBudgetRepository.save(existing);
        createAuditLog("UtilityBudget", saved.getId(), "UPDATE");
        return saved;
    }

    @Transactional
    public void deleteBudget(UUID id) {
        authorizationHelper.requireRole("system_admin", "mof_admin");

        utilityBudgetRepository.deleteById(id);
        createAuditLog("UtilityBudget", id, "DELETE");
    }

    private void createAuditLog(String entityType, UUID entityId, String action) {
        AuditLog auditLog = AuditLog.builder()
                .entityType(entityType)
                .entityId(entityId)
                .action(action)
                .changedBy(authorizationHelper.getCurrentUserId())
                .changedAt(OffsetDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
    }
}
