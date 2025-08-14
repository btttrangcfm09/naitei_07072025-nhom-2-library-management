package com.group2.library_management.exception;

public class EmptyImportDataException extends RuntimeException {
    public EmptyImportDataException(String columnName) {
        super(String.format("'%s' không được để trống.",columnName));
    }
}
