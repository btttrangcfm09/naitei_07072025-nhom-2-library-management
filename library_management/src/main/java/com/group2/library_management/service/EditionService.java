package com.group2.library_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import com.group2.library_management.dto.response.*;
import com.group2.library_management.entity.enums.BookStatus;

public interface EditionService {
    List<EditionResponse> getEditionsByBookId(Integer bookId);
    EditionDetailResponse getEditionDetailById(Integer id);
    void deleteEdition(Integer id);
    Page<EditionListResponse> searchEditionAndBookInstance(String keyword, String filterBy, BookStatus bookStatus, LocalDate fromDate, LocalDate toDate, Pageable pageable);
}
