package com.group2.library_management.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer userId) {
        super("Không tìm thấy người dùng với ID: " + userId);
    }
}
