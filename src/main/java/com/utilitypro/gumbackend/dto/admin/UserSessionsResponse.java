package com.utilitypro.gumbackend.dto.admin;

import java.time.OffsetDateTime;
import java.util.List;

public record UserSessionsResponse(List<SessionInfo> sessions) {
    public record SessionInfo(String sessionId, String ipAddress, String userAgent, OffsetDateTime lastAccessTime) {}
}
