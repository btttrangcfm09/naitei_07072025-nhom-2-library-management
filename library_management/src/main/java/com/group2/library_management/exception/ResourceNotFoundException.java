package com.group2.library_management.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(String resourceName, String columnName) {
        super(String.format(columnName + " '" + resourceName + "' không tồn tại trong hệ thống."));
    }
}
