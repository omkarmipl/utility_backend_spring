package com.utilitypro.gumbackend.dto.permission;

import java.util.List;

public record PermissionListResponse(List<PermissionItem> permissions) {
    public record PermissionItem(String permissionName, String description) {}
}
