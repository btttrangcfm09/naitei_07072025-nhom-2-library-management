package com.group2.library_management.dto.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.group2.library_management.dto.request.bookinstance.*;
import com.group2.library_management.dto.response.BookInstanceDetailResponse;
import com.group2.library_management.dto.response.BookInstanceResponse;
import com.group2.library_management.entity.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class BookInstanceMapper {

    public abstract BookInstance toEntity(CreateBookInstanceDto createBookInstanceDto);

    public abstract BookInstanceResponse toDto(BookInstance bookInstance);

    public BookInstanceDetailResponse mapToBookInstanceDetailResponse(BookInstance bookInstance) {
        if (bookInstance == null) {
            return null;
        }

        BookInstanceDetailResponse.BookInstanceDetailResponseBuilder bookInstanceDetailResponse = BookInstanceDetailResponse
                .builder()
                .id(bookInstance.getId())
                .barcode(bookInstance.getBarcode())
                .callNumber(bookInstance.getCallNumber())
                .acquiredDate(bookInstance.getAcquiredDate())
                .acquiredPrice(bookInstance.getAcquiredPrice())
                .status(bookInstance.getStatus())
                .note(bookInstance.getNote());

        Optional.ofNullable(bookInstance.getEdition()).ifPresent(edition -> {
            bookInstanceDetailResponse.editionTitle(edition.getTitle())
                    .coverImageUrl(edition.getCoverImageUrl())
                    .isbn(edition.getIsbn());

            Optional.ofNullable(edition.getBook()).ifPresent(book -> {
                bookInstanceDetailResponse.bookTitle(book.getTitle());
            });
        });
        return bookInstanceDetailResponse.build();
    }
}
