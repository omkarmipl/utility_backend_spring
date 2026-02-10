package com.utilitypro.gumbackend.dto.location;

public record LocationResponse(
        java.util.UUID id,
        String locationName,
        String code,
        Boolean isActive
) {}
