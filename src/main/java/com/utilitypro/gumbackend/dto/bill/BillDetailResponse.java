package com.utilitypro.gumbackend.dto.bill;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record BillDetailResponse(
        java.util.UUID id,
        String billNumber,
        BigDecimal amount,
        String status,
        OffsetDateTime dueDate,
        OffsetDateTime billDate,
        java.util.UUID accountId,
        String accountNumber
) {}
