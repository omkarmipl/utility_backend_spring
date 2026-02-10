package com.utilitypro.gumbackend.dto.department;

import java.util.List;

public record DepartmentListResponse(List<DepartmentItem> departments) {
    public record DepartmentItem(
            java.util.UUID id,
            String departmentName,
            String code,
            Boolean isActive
    ) {}
}
