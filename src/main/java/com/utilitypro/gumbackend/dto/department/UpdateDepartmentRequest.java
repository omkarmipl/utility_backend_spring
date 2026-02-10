package com.utilitypro.gumbackend.dto.department;

public record UpdateDepartmentRequest(
        String departmentName,
        String description,
        Boolean isActive
) {}
