package com.utilitypro.gumbackend.dto.account;
import jakarta.validation.constraints.NotNull;
public record CreateUtilityAccountRequest(@NotNull String accountNumber, @NotNull java.util.UUID providerId, @NotNull java.util.UUID departmentId, @NotNull java.util.UUID locationId) {}
