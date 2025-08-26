package com.group2.library_management.dto.response;

import java.time.LocalDateTime;

public record BorrowedBookSummaryResponse(
    String bookTitle,
    String coverImageUrl,
    LocalDateTime returnedDate
) {}
