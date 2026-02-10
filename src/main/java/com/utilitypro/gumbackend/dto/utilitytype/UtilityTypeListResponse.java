package com.utilitypro.gumbackend.dto.utilitytype;
import java.util.List;
public record UtilityTypeListResponse(List<TypeItem> utilityTypes) {
    public record TypeItem(java.util.UUID id, String typeName, Boolean isActive) {}
}
