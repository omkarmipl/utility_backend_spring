package com.utilitypro.gumbackend.dto.season;

public record SeasonListResponse(java.util.List<SeasonItem> seasons) {
    public record SeasonItem(java.util.UUID id, String seasonName, java.time.OffsetDateTime startDate, java.time.OffsetDateTime endDate, Boolean isActive) {}
}
