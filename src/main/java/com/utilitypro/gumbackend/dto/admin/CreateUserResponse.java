package com.utilitypro.gumbackend.dto.admin;

import java.util.UUID;

public record CreateUserResponse(UUID id, String email, String fullName, Boolean enabled) {}
