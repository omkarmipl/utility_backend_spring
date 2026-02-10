package com.utilitypro.gumbackend.mapper;

import com.utilitypro.gumbackend.domain.entity.AnomalyThreshold;
import com.utilitypro.gumbackend.dto.notification.AnomalyThresholdRequest;
import com.utilitypro.gumbackend.dto.notification.AnomalyThresholdResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for AnomalyThreshold entities and DTOs
 */
@Component
public class AnomalyThresholdMapper {

    public AnomalyThresholdResponse toResponse(AnomalyThreshold entity) {
        return AnomalyThresholdResponse.builder()
                .id(entity.getId())
                .utilityTypeId(entity.getUtilityType() != null ? entity.getUtilityType().getId() : null)
                .ministryId(entity.getMinistry() != null ? entity.getMinistry().getId() : null)
                // .thresholdType(entity.getThresholdType()) // Field doesn't exist - using thresholdMultiplier instead
                .thresholdValue(entity.getThresholdMultiplier()) // Using thresholdMultiplier as thresholdValue
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toLocalDateTime() : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toLocalDateTime() : null)
                .build();
    }

    public AnomalyThreshold toEntity(AnomalyThresholdRequest request) {
        // AnomalyThreshold doesn't have a builder, using setters
        AnomalyThreshold threshold = new AnomalyThreshold();
        // Note: Ministry and UtilityType relationships need to be set by the service layer
        // threshold.setUtilityTypeId(request.getUtilityTypeId()); // Not a direct field
        // threshold.setMinistryId(request.getMinistryId()); // Not a direct field
        // threshold.setThresholdType(request.getThresholdType()); // Field doesn't exist
        threshold.setThresholdMultiplier(request.getThresholdValue()); // Using thresholdValue as thresholdMultiplier
        threshold.setIsActive(request.getIsActive());
        return threshold;
    }

    public void updateEntityFromRequest(AnomalyThreshold entity, AnomalyThresholdRequest request) {
        // Note: Ministry and UtilityType relationships need to be updated by the service layer
        // entity.setUtilityTypeId(request.getUtilityTypeId()); // Not a direct field
        // entity.setMinistryId(request.getMinistryId()); // Not a direct field
        // entity.setThresholdType(request.getThresholdType()); // Field doesn't exist
        entity.setThresholdMultiplier(request.getThresholdValue()); // Using thresholdValue as thresholdMultiplier
        entity.setIsActive(request.getIsActive());
    }
}
