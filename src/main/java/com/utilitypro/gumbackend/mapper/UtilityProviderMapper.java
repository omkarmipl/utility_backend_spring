package com.utilitypro.gumbackend.mapper;

import com.utilitypro.gumbackend.domain.entity.UtilityProvider;
import com.utilitypro.gumbackend.dto.masterdata.UtilityProviderRequest;
import com.utilitypro.gumbackend.dto.masterdata.UtilityProviderResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for UtilityProvider entities and DTOs
 */
@Component
public class UtilityProviderMapper {

    public UtilityProviderResponse toResponse(UtilityProvider entity) {
        return UtilityProviderResponse.builder()
                .id(entity.getId())
                .utilityTypeId(entity.getUtilityTypeId())
                .name(entity.getName())
                .contactInfo(entity.getContactInfo())
                .address(entity.getAddress())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toLocalDateTime() : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toLocalDateTime() : null)
                .build();
    }

    public UtilityProvider toEntity(UtilityProviderRequest request) {
        return UtilityProvider.builder()
                .utilityTypeId(request.utilityTypeId())
                .name(request.name())
                .contactInfo(request.contactInfo())
                .address(request.address())
                .build();
    }

    public void updateEntityFromRequest(UtilityProvider entity, UtilityProviderRequest request) {
        entity.setUtilityTypeId(request.utilityTypeId());
        entity.setName(request.name());
        entity.setContactInfo(request.contactInfo());
        entity.setAddress(request.address());
    }
}
