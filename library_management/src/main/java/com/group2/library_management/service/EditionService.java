package com.group2.library_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.group2.library_management.dto.response.*;

public interface EditionService {
    Page<EditionListResponse> getAllEditions(Pageable pageable);
}
