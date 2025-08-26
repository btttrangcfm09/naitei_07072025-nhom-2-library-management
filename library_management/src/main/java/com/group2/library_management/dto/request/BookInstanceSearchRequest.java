package com.group2.library_management.dto.request;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;

public record BookInstanceSearchRequest(
        String keyword,
        String filterBy,
        Integer statusValue,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
        Integer page,
        Integer size
) {
    public BookInstanceSearchRequest {
        filterBy = Optional.ofNullable(filterBy).orElse("book_instance");
        page = Optional.ofNullable(page).orElse(1);
        size = Optional.ofNullable(size).orElse(20);
    }
}
