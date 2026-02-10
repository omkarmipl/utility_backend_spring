package com.utilitypro.gumbackend.dto.bill;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record BillListResponse(List<BillItem> bills, Integer totalCount) {
    public record BillItem(
            java.util.UUID id,
            String billNumber,
            BigDecimal amount,
            String status,
            OffsetDateTime dueDate
    ) {}
}
