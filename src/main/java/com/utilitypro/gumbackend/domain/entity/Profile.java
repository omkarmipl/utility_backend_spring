package com.utilitypro.gumbackend.domain.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false, columnDefinition = "text")
    private String email;

    @Column(name = "full_name", columnDefinition = "text")
    private String fullName;

    @Column(name = "first_name", columnDefinition = "text")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "text")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "department")
    private String department;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
        if (isActive == null) isActive = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public Profile(UUID id, UUID userId, String email, String fullName, String firstName, String lastName, String phoneNumber, String jobTitle, String department, Boolean isActive, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.jobTitle = jobTitle;
        this.department = department;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProfileBuilder builder() {
        return new ProfileBuilder();
    }

    public static class ProfileBuilder {
        private UUID id;
        private UUID userId;
        private String email;
        private String fullName;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String jobTitle;
        private String department;
        private Boolean isActive;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        ProfileBuilder() {}

        public ProfileBuilder id(UUID id) { this.id = id; return this; }
        public ProfileBuilder userId(UUID userId) { this.userId = userId; return this; }
        public ProfileBuilder email(String email) { this.email = email; return this; }
        public ProfileBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public ProfileBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public ProfileBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public ProfileBuilder phoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public ProfileBuilder jobTitle(String jobTitle) { this.jobTitle = jobTitle; return this; }
        public ProfileBuilder department(String department) { this.department = department; return this; }
        public ProfileBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public ProfileBuilder createdAt(OffsetDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ProfileBuilder updatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Profile build() {
            return new Profile(id, userId, email, fullName, firstName, lastName, phoneNumber, jobTitle, department, isActive, createdAt, updatedAt);
        }
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { 
        if (fullName == null && (firstName != null || lastName != null)) {
            return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
        }
        return fullName; 
    }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
