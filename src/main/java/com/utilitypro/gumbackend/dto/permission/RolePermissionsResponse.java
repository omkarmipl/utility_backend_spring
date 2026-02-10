package com.utilitypro.gumbackend.dto.permission;

import java.util.List;

public record RolePermissionsResponse(String roleName, List<String> permissions) {}
