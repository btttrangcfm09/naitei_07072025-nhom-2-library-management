package com.group2.library_management.controller.api;

import com.group2.library_management.dto.request.BookQueryParameters;
import com.group2.library_management.dto.response.BaseApiResponse;
import com.group2.library_management.dto.response.BookResponse;
import com.group2.library_management.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiBookController")
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Validated 
public class BookController {

    private final BookService bookService;
    private final MessageSource messageSource;

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    @GetMapping
    public ResponseEntity<BaseApiResponse<Page<BookResponse>>> getBooks(
        @Valid @ModelAttribute BookQueryParameters params
    ) {
        Page<BookResponse> bookPage = bookService.getAllBooks(params);
        
        String successMessage = getMessage("success.get.books");

        BaseApiResponse<Page<BookResponse>> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                bookPage,
                successMessage
        );
        
        return ResponseEntity.ok(response);
    }
}
