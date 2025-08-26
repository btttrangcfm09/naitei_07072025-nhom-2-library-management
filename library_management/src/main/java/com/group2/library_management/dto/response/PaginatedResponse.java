package com.group2.library_management.dto.response;

import org.springframework.data.domain.Page;
import java.util.List;

public record PaginatedResponse<T>(
    List<T> content,
    Metadata metadata
) {
    public record Metadata(
        int page,
        int size,
        long totalElements,
        int totalPages
    ) {}

    public static <T> PaginatedResponse<T> fromPage(Page<T> page) {
        var metadata = new Metadata(
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
        return new PaginatedResponse<>(page.getContent(), metadata);
    }
}
