package com.utilitypro.gumbackend.mapper;

import com.utilitypro.gumbackend.domain.entity.SystemSetting;
import com.utilitypro.gumbackend.dto.system.SystemSettingRequest;
import com.utilitypro.gumbackend.dto.system.SystemSettingResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for SystemSetting entities and DTOs
 */
@Component
public class SystemSettingMapper {

    public SystemSettingResponse toResponse(SystemSetting entity) {
        return SystemSettingResponse.builder()
                .id(entity.getId())
                .settingKey(entity.getSettingKey())
                .settingValue(entity.getSettingValue())
                .category(entity.getCategory())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toLocalDateTime() : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toLocalDateTime() : null)
                .build();
    }

    public SystemSetting toEntity(SystemSettingRequest request) {
        return SystemSetting.builder()
                .settingKey(request.settingKey())
                .settingValue(request.settingValue())
                .category(request.category())
                .build();
    }

    public void updateEntityFromRequest(SystemSetting entity, SystemSettingRequest request) {
        entity.setSettingKey(request.settingKey());
        entity.setSettingValue(request.settingValue());
        entity.setCategory(request.category());
    }
}
