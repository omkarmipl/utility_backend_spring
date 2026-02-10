package com.utilitypro.gumbackend.dto.location;

import java.util.List;

public record LocationListResponse(List<LocationItem> locations) {
    public record LocationItem(
            java.util.UUID id,
            String locationName,
            String code,
            Boolean isActive
    ) {}
}
