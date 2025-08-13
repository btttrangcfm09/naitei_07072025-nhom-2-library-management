package com.group2.library_management.entity.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum BookStatus {
    AVAILABLE(0),
    BORROWED(1),
    RESERVED(2),
    LOST(3),
    DAMAGED(4),
    REPAIRING(5),
    ARCHIVED(6);

    private final int value;

    BookStatus(int value) {
        this.value = value;
    }

    /**
     * Phương thức này tìm kiếm và trả về một Enum BookStatus dựa trên giá trị số được lưu trong database.
     * @param value giá trị số của trạng thái
     * @return BookStatus tương ứng
     */
    public static BookStatus fromValue(int value) {
        return Stream.of(BookStatus.values())
                .filter(status -> status.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid book status: " + value));
    }
}
