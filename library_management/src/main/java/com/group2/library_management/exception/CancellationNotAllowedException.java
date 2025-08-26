package com.group2.library_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) 
public class CancellationNotAllowedException extends RuntimeException {
    public CancellationNotAllowedException() {
    }
    
    public CancellationNotAllowedException(String message) {
        super(message);
    }
}
