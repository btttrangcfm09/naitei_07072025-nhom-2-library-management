package com.group2.library_management.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.group2.library_management.dto.request.bookinstance.*;
import com.group2.library_management.dto.response.BookInstanceResponse;
import com.group2.library_management.entity.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookInstanceMapper {
    BookInstance toEntity(CreateBookInstanceDto createBookInstanceDto);
    BookInstanceResponse toDto(BookInstance bookInstance);
}
