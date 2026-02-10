package com.utilitypro.gumbackend.dto.season;
import jakarta.validation.constraints.NotNull;
public record CreateSeasonRequest(@NotNull String seasonName, java.time.OffsetDateTime startDate, java.time.OffsetDateTime endDate) {}
