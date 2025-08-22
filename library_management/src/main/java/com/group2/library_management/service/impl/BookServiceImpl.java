package com.group2.library_management.service.impl;

import com.group2.library_management.common.constants.PaginationConstants;
import com.group2.library_management.dto.enums.MatchMode;
import com.group2.library_management.dto.mapper.BookMapper;
import com.group2.library_management.dto.request.BookQueryParameters;
import com.group2.library_management.dto.response.BookResponse;
import com.group2.library_management.entity.Book;
import com.group2.library_management.repository.BookRepository;
import com.group2.library_management.repository.specification.BookSpecification;
import com.group2.library_management.service.BookService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("id", "title"); 

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<BookResponse> getAllBooks(BookQueryParameters params) {

        int page = Optional.ofNullable(params.page()).orElse(PaginationConstants.DEFAULT_PAGE_NUMBER);
        int size = Optional.ofNullable(params.size()).orElse(PaginationConstants.DEFAULT_PAGE_SIZE);
        String[] sortParams = Optional.ofNullable(params.sort()).orElse(PaginationConstants.DEFAULT_SORT);
        
        // Xử lý tham số sắp xếp
        String sortField = sortParams[0];
        if (!StringUtils.hasText(sortField) || !ALLOWED_SORT_FIELDS.contains(sortField)) { 
            sortField = PaginationConstants.DEFAULT_SORT[0]; 
        }
        Sort.Direction direction = sortParams[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort.Order order = new Sort.Order(direction, sortField);
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        Specification<Book> spec = Specification.unrestricted();

        if (StringUtils.hasText(params.keyword())) {
            spec = spec.and(BookSpecification.searchByKeyword(params.keyword()));
        }

        if (!CollectionUtils.isEmpty(params.genreIds())) {
            MatchMode mode = Optional.ofNullable(params.genreMatchMode()).orElse(MatchMode.ANY); 

            if (mode == MatchMode.ALL) {
                spec = spec.and(BookSpecification.hasAllGenres(params.genreIds()));
            } else {
                spec = spec.and(BookSpecification.hasAnyGenre(params.genreIds()));
            }
        }

        if (!CollectionUtils.isEmpty(params.authorIds())) {
            MatchMode mode = Optional.ofNullable(params.authorMatchMode()).orElse(MatchMode.ANY);
            
            if (mode == MatchMode.ALL) {
                spec = spec.and(BookSpecification.hasAllAuthors(params.authorIds()));
            } else {
                spec = spec.and(BookSpecification.hasAnyAuthor(params.authorIds()));
            }
        }

        Page<Book> bookPage = bookRepository.findAll(spec, pageable);

        return bookPage.map(bookMapper::toBookResponse);
    }
}
