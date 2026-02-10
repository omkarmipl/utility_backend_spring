package com.utilitypro.gumbackend.mapper;

import com.utilitypro.gumbackend.domain.entity.AlertSetting;
import com.utilitypro.gumbackend.dto.notification.AlertSettingRequest;
import com.utilitypro.gumbackend.dto.notification.AlertSettingResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper for AlertSetting entities and DTOs
 */
@Component
public class AlertSettingMapper {

    public AlertSettingResponse toResponse(AlertSetting entity) {
        return AlertSettingResponse.builder()
                .id(entity.getId())
                .ministryId(entity.getMinistryId())
                // .departmentId(entity.getDepartmentId()) // Field doesn't exist yet
                // .alertType(entity.getAlertType()) // Field doesn't exist yet
                .isEnabled(entity.getEnabled()) // Using getEnabled() instead of getIsEnabled()
                // .emailRecipients(entity.getEmailRecipients()) // Using recipients instead
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toLocalDateTime() : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toLocalDateTime() : null)
                .build();
    }

    public AlertSetting toEntity(AlertSettingRequest request) {
        // AlertSetting entity doesn't have a builder, using setters instead
        AlertSetting setting = new AlertSetting();
        setting.setMinistryId(request.getMinistryId());
        //setting.setDepartmentId(request.getDepartmentId()); // Field doesn't exist yet
        //setting.setAlertType(request.getAlertType()); // Field doesn't exist yet
        setting.setEnabled(request.getIsEnabled()); // Using setEnabled instead
        //setting.setEmailRecipients(request.getEmailRecipients()); // Using recipients instead
        return setting;
    }

    public void updateEntityFromRequest(AlertSetting entity, AlertSettingRequest request) {
        entity.setMinistryId(request.getMinistryId());
        // entity.setDepartmentId(request.getDepartmentId()); // Field doesn't exist yet
        // entity.setAlertType(request.getAlertType()); // Field doesn't exist yet
        entity.setEnabled(request.getIsEnabled()); // Using setEnabled instead
        // entity.setEmailRecipients(request.getEmailRecipients()); // Using recipients instead
    }
}
