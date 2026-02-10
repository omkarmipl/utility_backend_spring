package com.utilitypro.gumbackend.dto.permission;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateRolePermissionsRequest(@NotNull String roleName, List<String> permissions) {}
