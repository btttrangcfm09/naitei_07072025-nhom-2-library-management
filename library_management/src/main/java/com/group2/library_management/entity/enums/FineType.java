package com.group2.library_management.entity.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum FineType {
    LATE_RETURN(0),
    DAMAGED(1),
    LOST(2);

    private final int value;

    FineType(int value) {
        this.value = value;
    }

    /**
     * Phương thức này tìm kiếm và trả về một Enum FineType dựa trên giá trị số được lưu trong database.
     * @param value giá trị số của loại phạt (vd: 1, 2, 3)
     * @return FineType tương ứng
     */
    public static FineType fromValue(int value) {
        return Stream.of(FineType.values())
                .filter(fineType -> fineType.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid fine type: " + value));
    }
}
