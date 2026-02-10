package com.utilitypro.gumbackend.dto.season;
public record UpdateSeasonRequest(String seasonName, java.time.OffsetDateTime startDate, java.time.OffsetDateTime endDate, Boolean isActive) {}
