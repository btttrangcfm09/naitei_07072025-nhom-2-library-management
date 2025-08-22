package com.group2.library_management.dto.response;

import java.util.List;

import com.group2.library_management.entity.enums.BookFormat;

public record CartItemResponse(
    Integer cartItemId, 
    Integer editionId,
    String title,
    String coverImageUrl,
    List<String> authors,
    List<String> genres,
    String language,
    BookFormat format,
    int quantity
) {}
