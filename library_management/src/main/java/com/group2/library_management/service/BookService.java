package com.group2.library_management.service;

import org.springframework.data.domain.Page;

import com.group2.library_management.dto.request.BookQueryParameters;
import com.group2.library_management.dto.response.BookResponse;

public interface BookService {
    Page<BookResponse> getAllBooks(BookQueryParameters params);
}
