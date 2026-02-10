package com.utilitypro.gumbackend.dto.utilitytype;
import jakarta.validation.constraints.NotNull;
public record CreateUtilityTypeRequest(@NotNull String typeName, String description) {}
