package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "utility_types")
@Getter
@Setter
@NoArgsConstructor
public class UtilityType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "unit_of_measure", nullable = false)
    private String unitOfMeasure;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    // Builder pattern
    public static UtilityTypeBuilder builder() {
        return new UtilityTypeBuilder();
    }

    public static class UtilityTypeBuilder {
        private UUID id;
        private String name;
        private String description;
        private String unitOfMeasure;
        private Boolean isActive;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public UtilityTypeBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UtilityTypeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UtilityTypeBuilder description(String description) {
            this.description = description;
            return this;
        }

        public UtilityTypeBuilder unitOfMeasure(String unitOfMeasure) {
            this.unitOfMeasure = unitOfMeasure;
            return this;
        }

        public UtilityTypeBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public UtilityTypeBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UtilityTypeBuilder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public UtilityType build() {
            UtilityType type = new UtilityType();
            type.id = this.id;
            type.name = this.name;
            type.description = this.description;
            type.unitOfMeasure = this.unitOfMeasure;
            type.isActive = this.isActive;
            type.createdAt = this.createdAt;
            type.updatedAt = this.updatedAt;
            return type;
        }
    }
}
