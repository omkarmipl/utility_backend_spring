package com.utilitypro.gumbackend.mapper;

import com.utilitypro.gumbackend.domain.entity.Department;
import com.utilitypro.gumbackend.dto.department.CreateDepartmentRequest;
import com.utilitypro.gumbackend.dto.department.DepartmentResponse;
import com.utilitypro.gumbackend.dto.department.UpdateDepartmentRequest;
import org.springframework.stereotype.Component;

/**
 * Mapper for Department entities and DTOs
 */
@Component
public class DepartmentMapper {

    public DepartmentResponse toResponse(Department entity) {
        // Using constructor directly or builder if present on DTO
        return new DepartmentResponse(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getIsActive()
        );
    }

    public Department toEntity(CreateDepartmentRequest request) {
        return Department.builder()
                .name(request.departmentName())
                .code(request.code())
                .description(request.description())
                .build();
        // Note: Ministry must be set by Service
    }

    public void updateEntityFromRequest(Department entity, UpdateDepartmentRequest request) {
        if (request.departmentName() != null) entity.setName(request.departmentName());
        if (request.description() != null) entity.setDescription(request.description());
        if (request.isActive() != null) entity.setIsActive(request.isActive());
    }
}
