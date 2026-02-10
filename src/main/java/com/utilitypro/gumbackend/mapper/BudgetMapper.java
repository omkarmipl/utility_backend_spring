package com.utilitypro.gumbackend.mapper;

import com.utilitypro.gumbackend.domain.entity.UtilityBudget;
import com.utilitypro.gumbackend.dto.business.BudgetRequest;
import com.utilitypro.gumbackend.dto.business.BudgetResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for Budget entities and DTOs
 */
@Component
public class BudgetMapper {

    public BudgetResponse toResponse(UtilityBudget entity) {
        return BudgetResponse.builder()
                .id(entity.getId())
                .ministryId(entity.getMinistry() != null ? entity.getMinistry().getId() : null)
                .departmentId(entity.getDepartment() != null ? entity.getDepartment().getId() : null)
                .utilityTypeId(entity.getUtilityType() != null ? entity.getUtilityType().getId() : null)
                .fiscalYear(entity.getFiscalYear() != null ? entity.getFiscalYear().toString() : null)
                .budgetedAmount(entity.getMonthlyBudgetAmt()) // Using monthlyBudgetAmt as budgetedAmount
                // .notes(entity.getNotes()) // Field doesn't exist in entity
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toLocalDateTime() : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toLocalDateTime() : null)
                .build();
    }

    public UtilityBudget toEntity(BudgetRequest request) {
        // UtilityBudget doesn't have a builder, using constructor and setters
        UtilityBudget budget = new UtilityBudget();
        // Note: Ministry, Department, and UtilityType relationships need to be set by the service layer
        // budget.setMinistryId(request.getMinistryId()); // Not a direct field
        // budget.setDepartmentId(request.getDepartmentId()); // Not a direct field
        // budget.setUtilityTypeId(request.getUtilityTypeId()); // Not a direct field
        budget.setFiscalYear(request.getFiscalYear() != null ? Integer.parseInt(request.getFiscalYear()) : null);
        budget.setMonthlyBudgetAmt(request.getBudgetedAmount()); // Using budgetedAmount as monthlyBudgetAmt
        // budget.setNotes(request.getNotes()); // Field doesn't exist in entity
        return budget;
    }

    public void updateEntityFromRequest(UtilityBudget entity, BudgetRequest request) {
        // Note: Ministry, Department, and UtilityType relationships need to be updated by the service layer
        // entity.setMinistryId(request.getMinistryId()); // Not a direct field
        // entity.setDepartmentId(request.getDepartmentId()); // Not a direct field
        // entity.setUtilityTypeId(request.getUtilityTypeId()); // Not a direct field
        entity.setFiscalYear(request.getFiscalYear() != null ? Integer.parseInt(request.getFiscalYear()) : null);
        entity.setMonthlyBudgetAmt(request.getBudgetedAmount()); // Using budgetedAmount as monthlyBudgetAmt
        // entity.setNotes(request.getNotes()); // Field doesn't exist in entity
    }
}
