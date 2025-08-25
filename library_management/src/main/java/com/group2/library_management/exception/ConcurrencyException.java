package com.group2.library_management.exception;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(String message) {
        super(message);
    }
}
