package com.group2.library_management.dto.response;

public record EditionSummaryResponse(
    Integer id,
    String title,
    String isbn,
    String publicationDate, 
    String publisher,
    Integer bookinstanceCount
) {}
