package com.group2.library_management.entity.converter;

import com.group2.library_management.entity.enums.FollowTargetType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FollowTargetTypeConverter implements AttributeConverter<FollowTargetType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(FollowTargetType targetType) {
        if (targetType == null) {
            return null;
        }
        return targetType.getValue();
    }

    @Override
    public FollowTargetType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return FollowTargetType.fromValue(dbData);
    }
}
