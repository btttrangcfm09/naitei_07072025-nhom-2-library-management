package com.group2.library_management.exception;

import java.util.List;

public class ImportValidationException extends RuntimeException {
    private final List<String> errors;

    public ImportValidationException(List<String> errors) {
        super("File import chứa lỗi. Vui lòng kiểm tra và thử lại.");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
