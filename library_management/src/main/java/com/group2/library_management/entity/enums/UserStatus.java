package com.group2.library_management.entity.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum UserStatus {
    INACTIVE(0),
    ACTIVE(1);

    private final int value;

    UserStatus(int value) {
        this.value = value;
    }

    /**
     * Phương thức này tìm kiếm và trả về một Enum UserStatus dựa trên giá trị số được lưu trong database.
     * @param value giá trị số của trạng thái (vd: 0, 1)
     * @return UserStatus tương ứng
     */
    public static UserStatus fromValue(int value) {
        return Stream.of(UserStatus.values())
                .filter(status -> status.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid user status: " + value));
    }
}
