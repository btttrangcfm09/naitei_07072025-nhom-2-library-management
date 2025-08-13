package com.group2.library_management.entity.converter;

import com.group2.library_management.entity.enums.UserStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserStatus status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    @Override
    public UserStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return UserStatus.fromValue(dbData);
    }
}
