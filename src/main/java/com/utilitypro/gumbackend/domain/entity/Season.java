package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "seasons")
@Getter
@Setter
@NoArgsConstructor
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "start_date")
    private java.time.LocalDate startDate;

    @Column(name = "end_date")
    private java.time.LocalDate endDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    // Builder pattern
    public static SeasonBuilder builder() {
        return new SeasonBuilder();
    }

    public static class SeasonBuilder {
        private UUID id;
        private String name;
        private String description;
        private java.time.LocalDate startDate;
        private java.time.LocalDate endDate;
        private Boolean isActive;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public SeasonBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public SeasonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SeasonBuilder description(String description) {
            this.description = description;
            return this;
        }

        public SeasonBuilder startDate(java.time.LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public SeasonBuilder endDate(java.time.LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public SeasonBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public SeasonBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public SeasonBuilder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Season build() {
            Season season = new Season();
            season.id = this.id;
            season.name = this.name;
            season.description = this.description;
            season.startDate = this.startDate;
            season.endDate = this.endDate;
            season.isActive = this.isActive;
            season.createdAt = this.createdAt;
            season.updatedAt = this.updatedAt;
            return season;
        }
    }
}
