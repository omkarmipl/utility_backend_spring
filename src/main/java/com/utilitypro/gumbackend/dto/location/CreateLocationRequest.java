package com.utilitypro.gumbackend.dto.location;

import jakarta.validation.constraints.NotNull;

public record CreateLocationRequest(
        @NotNull String locationName,
        @NotNull String code,
        String address
) {}
