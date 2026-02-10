package com.utilitypro.gumbackend.dto.admin;

import java.util.UUID;

public record UpdateUserResponse(UUID id, String email, String fullName, Boolean enabled) {}
