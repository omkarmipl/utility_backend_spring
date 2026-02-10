package com.utilitypro.gumbackend.dto.admin;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record UserListResponse(List<UserSummary> users, Integer totalCount, Integer page, Integer limit) {
    public record UserSummary(UUID id, String email, String fullName, List<String> roles, Boolean enabled, OffsetDateTime lastLogin) {}
}
