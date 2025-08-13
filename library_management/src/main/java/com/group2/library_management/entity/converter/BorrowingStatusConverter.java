package com.group2.library_management.entity.converter;

import com.group2.library_management.entity.enums.BorrowingStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BorrowingStatusConverter implements AttributeConverter<BorrowingStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BorrowingStatus status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    @Override
    public BorrowingStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return BorrowingStatus.fromValue(dbData);
    }
}
