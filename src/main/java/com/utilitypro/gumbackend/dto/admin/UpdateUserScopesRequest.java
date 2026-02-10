package com.utilitypro.gumbackend.dto.admin;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record UpdateUserScopesRequest(@NotNull List<UUID> ministryIds, @NotNull List<UUID> departmentIds) {}
