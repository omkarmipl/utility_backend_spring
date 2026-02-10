package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "utility_providers")
@Getter
@Setter
@NoArgsConstructor
public class UtilityProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "provider_type", nullable = false)
    private String providerType;

    @Column(name = "utility_type_id")
    private UUID utilityTypeId;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "address")
    private String address;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    // Builder pattern
    public static UtilityProviderBuilder builder() {
        return new UtilityProviderBuilder();
    }

    public static class UtilityProviderBuilder {
        private UUID id;
        private String name;
        private String providerType;
        private UUID utilityTypeId;
        private String contactInfo;
        private String address;
        private String contactEmail;
        private String contactPhone;
        private Boolean isActive;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public UtilityProviderBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UtilityProviderBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UtilityProviderBuilder providerType(String providerType) {
            this.providerType = providerType;
            return this;
        }

        public UtilityProviderBuilder utilityTypeId(UUID utilityTypeId) {
            this.utilityTypeId = utilityTypeId;
            return this;
        }

        public UtilityProviderBuilder contactInfo(String contactInfo) {
            this.contactInfo = contactInfo;
            return this;
        }

        public UtilityProviderBuilder address(String address) {
            this.address = address;
            return this;
        }

        public UtilityProviderBuilder contactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
            return this;
        }

        public UtilityProviderBuilder contactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
            return this;
        }

        public UtilityProviderBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public UtilityProviderBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UtilityProviderBuilder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public UtilityProvider build() {
            UtilityProvider provider = new UtilityProvider();
            provider.id = this.id;
            provider.name = this.name;
            provider.providerType = this.providerType;
            provider.utilityTypeId = this.utilityTypeId;
            provider.contactInfo = this.contactInfo;
            provider.address = this.address;
            provider.contactEmail = this.contactEmail;
            provider.contactPhone = this.contactPhone;
            provider.isActive = this.isActive;
            provider.createdAt = this.createdAt;
            provider.updatedAt = this.updatedAt;
            return provider;
        }
    }
}
