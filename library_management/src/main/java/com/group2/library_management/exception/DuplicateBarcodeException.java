package com.group2.library_management.exception;

public class DuplicateBarcodeException extends RuntimeException {
    public DuplicateBarcodeException(String barcode) {
        super(String.format("Barcode '%s' bị trùng lặp trong file.", barcode));
    }

    public DuplicateBarcodeException(String resourceName, String columnName) {
        super(String.format("%s '%s' đã tồn tại trong hệ thống.", columnName, resourceName));
    }
}
