package com.group2.library_management.dto.response;

import lombok.Builder;

@Builder
public record EditionListResponse(Integer id, String title, String isbn, String publisher, Integer publicationYear, Integer bookinstanceCount, String language) {
    
}
