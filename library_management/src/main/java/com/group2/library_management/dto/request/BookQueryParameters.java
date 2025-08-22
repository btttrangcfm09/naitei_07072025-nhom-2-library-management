package com.group2.library_management.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.util.List;

import com.group2.library_management.dto.enums.MatchMode;

public record BookQueryParameters(
    // Search
    String keyword,

    // Filter
    List<Integer> genreIds,
    MatchMode genreMatchMode,
    
    List<Integer> authorIds,
    MatchMode authorMatchMode,
    
    // Pagination
    @Min(value = 0, message = "{validation.page.min}")
    Integer page, 

    @Min(value = 1, message = "{validation.size.min}")
    @Max(value = 100, message = "{validation.size.max}")
    Integer size, 

    // Sort
    String[] sort
) {}
