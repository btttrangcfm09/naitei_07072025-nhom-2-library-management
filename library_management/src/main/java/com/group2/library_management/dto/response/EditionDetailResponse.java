package com.group2.library_management.dto.response;

import java.time.LocalDate;
import java.util.List;

public record EditionDetailResponse(
        Integer id,
        String title,
        String bookTitle,
        List<String> authors,
        String isbn,
        String publisherName,
        LocalDate publicationDate,
        String language,
        Integer pageNumber,
        String coverImageUrl,
        String format,
        String description,
        int initialQuantity,
        int availableQuantity
) {

}
