package com.utilitypro.gumbackend.dto.provider;
import jakarta.validation.constraints.NotNull;
public record CreateUtilityProviderRequest(@NotNull String providerName, @NotNull java.util.UUID utilityTypeId, String contactInfo) {}
