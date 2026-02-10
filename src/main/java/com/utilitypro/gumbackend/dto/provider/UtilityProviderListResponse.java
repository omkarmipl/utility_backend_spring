package com.utilitypro.gumbackend.dto.provider;
import java.util.List;
public record UtilityProviderListResponse(List<ProviderItem> providers) {
    public record ProviderItem(java.util.UUID id, String providerName, String utilityType, Boolean isActive) {}
}
