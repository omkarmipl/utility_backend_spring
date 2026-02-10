package com.utilitypro.gumbackend.dto.search;

import java.util.List;

public record GlobalSearchResponse(List<SearchResultItem> results) {
    public record SearchResultItem(
            String id,
            String title,
            String type,
            String description,
            String url
    ) {}
}
