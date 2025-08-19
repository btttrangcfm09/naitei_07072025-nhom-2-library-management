package com.group2.library_management.exception;

public class InvalidPrincipalTypeException extends RuntimeException {
    public InvalidPrincipalTypeException() {
        super();
    }

    public InvalidPrincipalTypeException(String message) {
        super(message);
    }
}
