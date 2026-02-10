package com.utilitypro.gumbackend.dto.bill;

import java.util.List;

public record UnpaidBillListResponse(List<BillItem> bills, java.math.BigDecimal totalUnpaidAmount) {
     public record BillItem(java.util.UUID id, java.math.BigDecimal amount, java.time.OffsetDateTime dueDate) {}
}
