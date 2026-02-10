package com.utilitypro.gumbackend.dto.bill;

import jakarta.validation.constraints.NotNull;

public record UpdateBillStatusRequest(@NotNull String status, String notes) {}
