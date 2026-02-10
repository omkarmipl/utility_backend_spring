package com.utilitypro.gumbackend.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

/**
 * Generic paginated response wrapper
 * @param <T> Type of content in the page
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    
    private List<T> content;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
    private boolean empty;
    
    /**
     * Create PageResponse from Spring Data Page
     */
    public static <T> PageResponse<T> of(Page<T> page) {
        return PageResponse.<T>builder()
                .content(page.getContent())
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();
    }
    
    /**
     * Create PageResponse from Spring Data Page with mapping function
     */
    public static <T, R> PageResponse<R> of(Page<T> page, Function<T, R> mapper) {
        return PageResponse.<R>builder()
                .content(page.getContent().stream().map(mapper).toList())
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();
    }
    
    /**
     * Create PageResponse from Page metadata and pre-mapped content list
     */
    public static <T, R> PageResponse<R> fromPageAndList(Page<T> page, List<R> mappedContent) {
        return PageResponse.<R>builder()
                .content(mappedContent)
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();
    }
}
