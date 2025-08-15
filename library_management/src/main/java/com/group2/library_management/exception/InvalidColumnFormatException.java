package com.group2.library_management.exception;

public class InvalidColumnFormatException extends RuntimeException {
    public InvalidColumnFormatException(String columnName, String expectedFormat) {
        super(String.format("Cột '%s' có định dạng không hợp lệ. Vui lòng sử dụng định dạng: %s", columnName, expectedFormat));
    }

    public InvalidColumnFormatException(String message) {
        super(message);
    }
}
