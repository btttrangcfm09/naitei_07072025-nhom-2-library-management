package com.group2.library_management.entity.converter;

import com.group2.library_management.entity.enums.RoleType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleTypeConverter implements AttributeConverter<RoleType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(RoleType role) {
        if (role == null) {
            return null;
        }
        return role.getValue();
    }

    @Override
    public RoleType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return RoleType.fromValue(dbData);
    }
}
