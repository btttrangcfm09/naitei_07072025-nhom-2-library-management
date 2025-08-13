package com.group2.library_management.entity.enums;

import lombok.Getter;
import java.util.stream.Stream;

@Getter
public enum BookFormat {
    HARDCOVER(0),
    SOFTCOVER(1),
    EBOOK(2);

    private final int value;

    BookFormat(int value) {
        this.value = value;
    }

    /**
     * Tìm kiếm và trả về một enum BookFormat từ giá trị code.
     * 
     * @param code giá trị số của định dạng sách
     * @return BookFormat tương ứng
     * @throws IllegalArgumentException nếu không tìm thấy code hợp lệ
     */
    public static BookFormat fromValue(int value) {
        return Stream.of(BookFormat.values())
                .filter(format -> format.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid BookFormat code: " + value));
    }
}
