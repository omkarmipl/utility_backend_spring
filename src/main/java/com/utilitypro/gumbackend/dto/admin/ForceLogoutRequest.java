package com.utilitypro.gumbackend.dto.admin;

import jakarta.validation.constraints.NotNull;

public record ForceLogoutRequest(@NotNull String reason) {}
