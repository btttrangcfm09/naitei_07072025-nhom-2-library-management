package com.group2.library_management.entity.converter;

import com.group2.library_management.entity.enums.BookStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BookStatusConverter implements AttributeConverter<BookStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookStatus status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    @Override
    public BookStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return BookStatus.fromValue(dbData);
    }
    
}
