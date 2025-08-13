package com.group2.library_management.entity.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum FollowTargetType {
    AUTHOR(0),      
    PUBLISHER(1),   
    EDITION(2);     

    private final int value;

    FollowTargetType(int value) {
        this.value = value;
    }

    /**
     * Phương thức này tìm kiếm và trả về một Enum FollowTargetType dựa trên giá trị số được lưu trong database.
     * @param value giá trị số của loại mục tiêu (vd: 1, 2, 3)
     * @return FollowTargetType tương ứng
     */
    public static FollowTargetType fromValue(int value) {
        return Stream.of(FollowTargetType.values())
                .filter(targetType -> targetType.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid follow target type: " + value));
    }
}
