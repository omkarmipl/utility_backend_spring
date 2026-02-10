package com.utilitypro.gumbackend.mapper;

import com.utilitypro.gumbackend.domain.entity.Season;
import com.utilitypro.gumbackend.dto.system.SeasonRequest;
import com.utilitypro.gumbackend.dto.system.SeasonResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for Season entities and DTOs
 */
@Component
public class SeasonMapper {

    public SeasonResponse toResponse(Season entity) {
        return SeasonResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toLocalDateTime() : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toLocalDateTime() : null)
                .build();
    }

    public Season toEntity(SeasonRequest request) {
        return Season.builder()
                .name(request.name())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
    }

    public void updateEntityFromRequest(Season entity, SeasonRequest request) {
        entity.setName(request.name());
        entity.setStartDate(request.startDate());
        entity.setEndDate(request.endDate());
    }
}
