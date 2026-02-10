package com.utilitypro.gumbackend.dto.user;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * User scopes response DTO
 */
@NoArgsConstructor
public class UserScopesResponse {
    private List<ScopeDTO> scopes;
    
    public UserScopesResponse(List<ScopeDTO> scopes) {
        this.scopes = scopes;
    }

    public static UserScopesResponseBuilder builder() {
        return new UserScopesResponseBuilder();
    }

    public static class UserScopesResponseBuilder {
        private List<ScopeDTO> scopes;

        UserScopesResponseBuilder() {}

        public UserScopesResponseBuilder scopes(List<ScopeDTO> scopes) {
            this.scopes = scopes;
            return this;
        }

        public UserScopesResponse build() {
            return new UserScopesResponse(scopes);
        }
    }

    public List<ScopeDTO> getScopes() {
        return scopes;
    }

    public void setScopes(List<ScopeDTO> scopes) {
        this.scopes = scopes;
    }

    @NoArgsConstructor
    public static class ScopeDTO {
        private UUID ministryId;
        private String ministryName;
        private UUID departmentId;
        private String departmentName;

        public ScopeDTO(UUID ministryId, String ministryName, UUID departmentId, String departmentName) {
            this.ministryId = ministryId;
            this.ministryName = ministryName;
            this.departmentId = departmentId;
            this.departmentName = departmentName;
        }

        public static ScopeDTOBuilder builder() {
            return new ScopeDTOBuilder();
        }

        public static class ScopeDTOBuilder {
            private UUID ministryId;
            private String ministryName;
            private UUID departmentId;
            private String departmentName;

            ScopeDTOBuilder() {}

            public ScopeDTOBuilder ministryId(UUID ministryId) {
                this.ministryId = ministryId;
                return this;
            }

            public ScopeDTOBuilder ministryName(String ministryName) {
                this.ministryName = ministryName;
                return this;
            }

            public ScopeDTOBuilder departmentId(UUID departmentId) {
                this.departmentId = departmentId;
                return this;
            }

            public ScopeDTOBuilder departmentName(String departmentName) {
                this.departmentName = departmentName;
                return this;
            }

            public ScopeDTO build() {
                return new ScopeDTO(ministryId, ministryName, departmentId, departmentName);
            }
        }

        public UUID getMinistryId() { return ministryId; }
        public void setMinistryId(UUID ministryId) { this.ministryId = ministryId; }

        public String getMinistryName() { return ministryName; }
        public void setMinistryName(String ministryName) { this.ministryName = ministryName; }

        public UUID getDepartmentId() { return departmentId; }
        public void setDepartmentId(UUID departmentId) { this.departmentId = departmentId; }

        public String getDepartmentName() { return departmentName; }
        public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    }
}
