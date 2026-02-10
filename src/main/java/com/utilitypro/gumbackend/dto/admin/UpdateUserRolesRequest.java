package com.utilitypro.gumbackend.dto.admin;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateUserRolesRequest(@NotNull List<String> roles) {}
