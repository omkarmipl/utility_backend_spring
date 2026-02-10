package com.utilitypro.gumbackend.mapper;

import com.utilitypro.gumbackend.domain.entity.UtilityType;
import com.utilitypro.gumbackend.dto.masterdata.UtilityTypeRequest;
import com.utilitypro.gumbackend.dto.masterdata.UtilityTypeResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for UtilityType entities and DTOs
 */
@Component
public class UtilityTypeMapper {

    public UtilityTypeResponse toResponse(UtilityType entity) {
        return UtilityTypeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toLocalDateTime() : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toLocalDateTime() : null)
                .build();
    }

    public UtilityType toEntity(UtilityTypeRequest request) {
        return UtilityType.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public void updateEntityFromRequest(UtilityType entity, UtilityTypeRequest request) {
        entity.setName(request.name());
        entity.setDescription(request.description());
    }
}
