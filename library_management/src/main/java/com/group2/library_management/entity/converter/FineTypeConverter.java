package com.group2.library_management.entity.converter;

import com.group2.library_management.entity.enums.FineType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FineTypeConverter implements AttributeConverter<FineType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(FineType fineType) {
        if (fineType == null) {
            return null;
        }
        return fineType.getValue();
    }

    @Override
    public FineType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return FineType.fromValue(dbData);
    }
}
