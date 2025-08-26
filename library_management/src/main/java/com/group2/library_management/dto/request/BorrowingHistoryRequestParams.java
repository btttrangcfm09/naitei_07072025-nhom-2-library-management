package com.group2.library_management.dto.request;

import com.group2.library_management.entity.enums.BorrowingStatus;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

public record BorrowingHistoryRequestParams(
    List<BorrowingStatus> statuses,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
    Integer page,
    Integer size,
    String sort,
    String dir
) {}
