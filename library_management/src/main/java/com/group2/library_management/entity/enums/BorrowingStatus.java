package com.group2.library_management.entity.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum BorrowingStatus {
    PENDING(0),
    APPROVED(1),
    BORROWED(2),
    REJECTED(3),
    RETURNED(4),
    OVERDUE(5),
    LOST_REPORTED(6),
    CANCELLED(7); 

    private final int value;

    BorrowingStatus(int value) {
        this.value = value;
    }

    /**
     * Phương thức này tìm kiếm và trả về một Enum BorrowingStatus dựa trên giá trị số được lưu trong database.
     * @param value giá trị số của trạng thái
     * @return BorrowingStatus tương ứng
     */
    public static BorrowingStatus fromValue(int value) {
        return Stream.of(BorrowingStatus.values())
                .filter(status -> status.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid borrowing status: " + value));
    }
}
