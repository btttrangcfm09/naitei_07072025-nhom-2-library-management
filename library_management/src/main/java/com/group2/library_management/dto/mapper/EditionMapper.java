package com.group2.library_management.dto.mapper;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.group2.library_management.dto.response.EditionListResponse;
import com.group2.library_management.entity.BookInstance;
import com.group2.library_management.entity.Edition;

import lombok.RequiredArgsConstructor;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@RequiredArgsConstructor
public abstract class EditionMapper {
    public EditionListResponse toDto(Edition edition) {
        if (edition == null) {
            return null;
        }
        EditionListResponse.EditionListResponseBuilder editionResponse =  EditionListResponse.builder()
                .id(edition.getId())
                .title(edition.getTitle())
                .isbn(edition.getIsbn() != null ? edition.getIsbn() : null)
                .publisher(edition.getPublisher() != null ? edition.getPublisher().getName() : null)
                .publicationYear(edition.getPublicationDate() != null ? edition.getPublicationDate().getYear() : null)
                .language(edition.getLanguage() != null ? edition.getLanguage() : null);
        List<BookInstance> bookInstances = Optional.ofNullable(edition.getBookInstances()).orElse(Collections.emptyList());
        int count = bookInstances.size();
        return editionResponse.bookinstanceCount(count).build();   
    }
    
} 
