package com.group2.library_management.exception;

public class CannotDeleteResourceException extends RuntimeException{
    public CannotDeleteResourceException(String message){
        super(message);
    }
}
