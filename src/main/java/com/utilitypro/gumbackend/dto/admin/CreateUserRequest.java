package com.utilitypro.gumbackend.dto.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Create user request DTO
 */
@NoArgsConstructor
public class CreateUserRequest {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;
    
    private String fullName;
    private String phoneNumber;
    private String jobTitle;
    private String department;
    
    @Valid
    private List<String> roles;
    
    @Valid
    private List<ScopeDTO> scopes;

    public CreateUserRequest(String email, String password, String fullName, String phoneNumber, String jobTitle, String department, List<String> roles, List<ScopeDTO> scopes) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.jobTitle = jobTitle;
        this.department = department;
        this.roles = roles;
        this.scopes = scopes;
    }

    public static CreateUserRequestBuilder builder() {
        return new CreateUserRequestBuilder();
    }

    public static class CreateUserRequestBuilder {
        private String email;
        private String password;
        private String fullName;
        private String phoneNumber;
        private String jobTitle;
        private String department;
        private List<String> roles;
        private List<ScopeDTO> scopes;

        CreateUserRequestBuilder() {}

        public CreateUserRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CreateUserRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public CreateUserRequestBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public CreateUserRequestBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CreateUserRequestBuilder jobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public CreateUserRequestBuilder department(String department) {
            this.department = department;
            return this;
        }

        public CreateUserRequestBuilder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public CreateUserRequestBuilder scopes(List<ScopeDTO> scopes) {
            this.scopes = scopes;
            return this;
        }

        public CreateUserRequest build() {
            return new CreateUserRequest(email, password, fullName, phoneNumber, jobTitle, department, roles, scopes);
        }
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public List<ScopeDTO> getScopes() { return scopes; }
    public void setScopes(List<ScopeDTO> scopes) { this.scopes = scopes; }
    
    @NoArgsConstructor
    public static class ScopeDTO {
        private UUID ministryId;
        private UUID departmentId;

        public ScopeDTO(UUID ministryId, UUID departmentId) {
            this.ministryId = ministryId;
            this.departmentId = departmentId;
        }

        public static ScopeDTOBuilder builder() {
            return new ScopeDTOBuilder();
        }

        public static class ScopeDTOBuilder {
            private UUID ministryId;
            private UUID departmentId;

            ScopeDTOBuilder() {}

            public ScopeDTOBuilder ministryId(UUID ministryId) {
                this.ministryId = ministryId;
                return this;
            }

            public ScopeDTOBuilder departmentId(UUID departmentId) {
                this.departmentId = departmentId;
                return this;
            }

            public ScopeDTO build() {
                return new ScopeDTO(ministryId, departmentId);
            }
        }

        public UUID getMinistryId() { return ministryId; }
        public void setMinistryId(UUID ministryId) { this.ministryId = ministryId; }

        public UUID getDepartmentId() { return departmentId; }
        public void setDepartmentId(UUID departmentId) { this.departmentId = departmentId; }
    }
}
