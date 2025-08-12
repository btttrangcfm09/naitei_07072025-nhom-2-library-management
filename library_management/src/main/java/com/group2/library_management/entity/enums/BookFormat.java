package com.group2.library_management.entity.enums;

import lombok.Getter; 
import java.util.stream.Stream; 

@Getter 
public enum BookFormat {
    HARDCOVER(1, "Hard Cover"),
    SOFTCOVER(2, "Soft Cover"),
    EBOOK(3, "Ebook");

    private final int value;
    private final String formatString;
    BookFormat(int value,String formatString) {
        this.value = value;
        this.formatString = formatString;
    }


    /**
     * Tìm kiếm và trả về một enum BookFormat từ giá trị code.
     * @param code giá trị số của định dạng sách 
     * @return BookFormat tương ứng
     * @throws IllegalArgumentException nếu không tìm thấy code hợp lệ
     */
    // 5. Viết lại phương thức fromCode() bằng Stream API
    public static BookFormat fromValue(int value) {
        return Stream.of(BookFormat.values()) 
                .filter(format -> format.getValue() == value) 
                .findFirst() 
                .orElseThrow(() -> new IllegalArgumentException("Invalid BookFormat code: " + value)); 
    }
}

