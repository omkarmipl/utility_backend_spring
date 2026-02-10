package com.utilitypro.gumbackend.dto.user;

import lombok.NoArgsConstructor;

import java.util.List;

/**
 * User roles response DTO
 */
@NoArgsConstructor
public class UserRolesResponse {
    private List<String> roles;

    public UserRolesResponse(List<String> roles) {
        this.roles = roles;
    }

    public static UserRolesResponseBuilder builder() {
        return new UserRolesResponseBuilder();
    }

    public static class UserRolesResponseBuilder {
        private List<String> roles;

        UserRolesResponseBuilder() {}

        public UserRolesResponseBuilder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public UserRolesResponse build() {
            return new UserRolesResponse(roles);
        }
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
