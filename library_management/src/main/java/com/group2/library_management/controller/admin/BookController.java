package com.group2.library_management.controller.admin;

import com.group2.library_management.dto.request.BookQueryParameters;
import com.group2.library_management.dto.response.BookResponse;
import com.group2.library_management.service.BookService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminBookController")
@RequestMapping("/admin/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public String showBookList(
            @ModelAttribute BookQueryParameters params,
            Model model
    ) {
        int oneBasedPage = Optional.ofNullable(params.page()).orElse(1);
        int zeroBasedPage = Math.max(0, oneBasedPage - 1);

        BookQueryParameters correctedParams = new BookQueryParameters(
                params.keyword(),
                params.genreIds(),
                params.genreMatchMode(),
                params.authorIds(),
                params.authorMatchMode(),
                zeroBasedPage,      
                params.size(),
                params.sort()
        );

        Page<BookResponse> bookPage = bookService.getAllBooks(correctedParams);

        // Add the retrieved data and search parameters to the model for the view
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("keyword", correctedParams.keyword());

        // Return the logical view name for Thymeleaf to resolve
        return "admin/book/list";
    }
}
