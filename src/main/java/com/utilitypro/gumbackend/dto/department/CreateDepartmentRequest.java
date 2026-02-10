package com.utilitypro.gumbackend.dto.department;

import jakarta.validation.constraints.NotNull;

public record CreateDepartmentRequest(
        @NotNull String departmentName,
        @NotNull java.util.UUID ministryId,
        @NotNull String code,
        String description
) {}
