package com.group2.library_management.dto.response;

import java.time.LocalDate;

import com.group2.library_management.entity.enums.BookFormat;

public record EditionUpdateResponse(
    Integer id,
    String title,
    String isbn,
    Integer publisherId,
    LocalDate publicationDate,
    Integer pageNumber,
    String language,
    BookFormat format,
    String coverImageUrl,
    int initialQuantity,
    int availableQuantity,
    Long version
) {}
