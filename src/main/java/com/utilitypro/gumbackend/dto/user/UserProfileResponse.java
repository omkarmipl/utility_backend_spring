package com.utilitypro.gumbackend.dto.user;

import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * User profile response DTO
 */
@NoArgsConstructor
public class UserProfileResponse {
    private UUID userId;
    private String fullName;
    private String phoneNumber;
    private String jobTitle;
    private String department;

    public UserProfileResponse(UUID userId, String fullName, String phoneNumber, String jobTitle, String department) {
        this.userId = userId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.jobTitle = jobTitle;
        this.department = department;
    }

    public static UserProfileResponseBuilder builder() {
        return new UserProfileResponseBuilder();
    }

    public static class UserProfileResponseBuilder {
        private UUID userId;
        private String fullName;
        private String phoneNumber;
        private String jobTitle;
        private String department;

        UserProfileResponseBuilder() {}

        public UserProfileResponseBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public UserProfileResponseBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserProfileResponseBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserProfileResponseBuilder jobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public UserProfileResponseBuilder department(String department) {
            this.department = department;
            return this;
        }

        public UserProfileResponse build() {
            return new UserProfileResponse(userId, fullName, phoneNumber, jobTitle, department);
        }
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
