package com.group2.library_management.entity.converter;

import com.group2.library_management.entity.enums.BookFormat;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BookFormatConverter implements AttributeConverter<BookFormat, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BookFormat bookFormat) {
        if (bookFormat == null) {
            return null;
        }
        return bookFormat.getValue();
    }

    @Override
    public BookFormat convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return BookFormat.fromValue(dbData);
    }
}
