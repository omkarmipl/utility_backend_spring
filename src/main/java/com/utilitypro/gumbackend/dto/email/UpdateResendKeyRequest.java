package com.utilitypro.gumbackend.dto.email;

import jakarta.validation.constraints.NotBlank;

public record UpdateResendKeyRequest(@NotBlank String apiKey) {}
