package com.utilitypro.gumbackend.dto.account;
import java.util.List;
public record UtilityAccountListResponse(List<AccountItem> accounts) {
    public record AccountItem(java.util.UUID id, String accountNumber, String utilityType, String providerName, Boolean isActive) {}
}
