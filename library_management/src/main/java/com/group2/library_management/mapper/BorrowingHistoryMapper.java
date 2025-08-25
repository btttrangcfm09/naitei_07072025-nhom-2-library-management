package com.group2.library_management.mapper;

import com.group2.library_management.dto.response.BorrowedBookSummaryResponse;
import com.group2.library_management.dto.response.BorrowingHistoryResponse;
import com.group2.library_management.entity.BorrowingDetail;
import com.group2.library_management.entity.BorrowingReceipt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BorrowingHistoryMapper {

    @Mapping(source = "borrowingDetails", target = "borrowedBooks")
    BorrowingHistoryResponse toBorrowingHistoryResponse(BorrowingReceipt receipt);

    @Mapping(source = "bookInstance.edition.book.title", target = "bookTitle")
    @Mapping(source = "bookInstance.edition.coverImageUrl", target = "coverImageUrl")
    @Mapping(source = "refundDate", target = "returnedDate")
    BorrowedBookSummaryResponse toBorrowedBookSummaryResponse(BorrowingDetail detail);
}
