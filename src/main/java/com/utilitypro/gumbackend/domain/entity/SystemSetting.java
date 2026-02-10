package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "system_settings")
@Getter
@Setter
@NoArgsConstructor
public class SystemSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "setting_key", unique = true)
    private String settingKey;

    @Column(name = "setting_value", nullable = false, columnDefinition = "text")
    private String settingValue;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    // Builder pattern
    public static SystemSettingBuilder builder() {
        return new SystemSettingBuilder();
    }

    public static class SystemSettingBuilder {
        private UUID id;
        private String settingKey;
        private String settingValue;
        private String description;
        private String category;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public SystemSettingBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public SystemSettingBuilder settingKey(String settingKey) {
            this.settingKey = settingKey;
            return this;
        }

        public SystemSettingBuilder settingValue(String settingValue) {
            this.settingValue = settingValue;
            return this;
        }

        public SystemSettingBuilder description(String description) {
            this.description = description;
            return this;
        }

        public SystemSettingBuilder category(String category) {
            this.category = category;
            return this;
        }

        public SystemSettingBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public SystemSettingBuilder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public SystemSetting build() {
            SystemSetting setting = new SystemSetting();
            setting.id = this.id;
            setting.settingKey = this.settingKey;
            setting.settingValue = this.settingValue;
            setting.description = this.description;
            setting.category = this.category;
            setting.createdAt = this.createdAt;
            setting.updatedAt = this.updatedAt;
            return setting;
        }
    }
}
