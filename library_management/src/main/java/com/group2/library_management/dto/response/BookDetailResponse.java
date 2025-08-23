package com.group2.library_management.dto.response;

import java.util.List;

public record BookDetailResponse(
    Integer id,
    String title,
    String description,
    List<String> authors, 
    List<String> genres,  
    List<EditionSummaryResponse> editions 
) {}
