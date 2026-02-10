package com.utilitypro.gumbackend.dto.admin;

import jakarta.validation.constraints.Email;
import lombok.NoArgsConstructor;

/**
 * Update user request DTO
 */
@NoArgsConstructor
public class UpdateUserRequest {
    
    @Email(message = "Email must be valid")
    private String email;
    
    private String fullName;
    private String phoneNumber;
    private String jobTitle;
    private String department;

    public UpdateUserRequest(String email, String fullName, String phoneNumber, String jobTitle, String department) {
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.jobTitle = jobTitle;
        this.department = department;
    }

    public static UpdateUserRequestBuilder builder() {
        return new UpdateUserRequestBuilder();
    }

    public static class UpdateUserRequestBuilder {
        private String email;
        private String fullName;
        private String phoneNumber;
        private String jobTitle;
        private String department;

        UpdateUserRequestBuilder() {}

        public UpdateUserRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UpdateUserRequestBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UpdateUserRequestBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UpdateUserRequestBuilder jobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public UpdateUserRequestBuilder department(String department) {
            this.department = department;
            return this;
        }

        public UpdateUserRequest build() {
            return new UpdateUserRequest(email, fullName, phoneNumber, jobTitle, department);
        }
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
