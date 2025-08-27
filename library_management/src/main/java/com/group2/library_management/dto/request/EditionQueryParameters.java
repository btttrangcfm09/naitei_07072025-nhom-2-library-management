package com.group2.library_management.dto.request;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record EditionQueryParameters(
    // Search
    String keyword,

    // Filter
    List<Integer> publicationYears, 
    List<Integer> publisherIds,     
    List<String> languages,       

    // Pagination
    @Min(value = 0, message = "{validation.page.min}")
    Integer page, 

    @Min(value = 1, message = "{validation.size.min}")
    @Max(value = 100, message = "{validation.size.max}")
    Integer size, 

    // Sort
    String[] sort
) {}
