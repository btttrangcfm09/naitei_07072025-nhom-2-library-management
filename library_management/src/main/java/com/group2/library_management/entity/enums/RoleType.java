package com.group2.library_management.entity.enums;

import java.util.stream.Stream;

import lombok.Getter;

@Getter 
public enum RoleType {
    CLIENT(0, "ROLE_CLIENT"),
    ADMIN(1, "ROLE_ADMIN");

    private final int value;
    private final String authority;

    RoleType(int value, String authority) {
        this.value = value;
        this.authority = authority;
    }

    /**
     * Phương thức này tìm kiếm và trả về một Enum RoleType dựa trên giá trị số được lưu trong database.
     * @param value giá trị số của vai trò (vd: 1, 2)
     * @return RoleType tương ứng
     */
    public static RoleType fromValue(int value) {
        return Stream.of(RoleType.values())
                .filter(role -> role.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + value));
    }
}
