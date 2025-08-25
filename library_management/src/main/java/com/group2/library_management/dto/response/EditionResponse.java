package com.group2.library_management.dto.response;

import com.group2.library_management.entity.enums.DeletionStatus;

public record EditionResponse(
    Integer id,
    String title,
    String isbn,
    String publisherName,
    Integer publicationYear,
    int availableQuantity,
    DeletionStatus deletionStatus
) {}
