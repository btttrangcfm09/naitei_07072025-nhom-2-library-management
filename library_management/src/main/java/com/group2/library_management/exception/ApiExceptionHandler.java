package com.group2.library_management.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.group2.library_management.dto.response.BaseApiResponse;
import com.group2.library_management.dto.response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice(basePackages = "com.group2.library_management.controller.api")
public class ApiExceptionHandler {

    // xử lý lỗi không tìm thấy tài nguyên
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseApiResponse<ErrorResponse>> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        // tạo đối tượng ErrorResponse chi tiết
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
        );

        // đóng gói ErrorResponse vào BaseApiResponse
        BaseApiResponse<ErrorResponse> apiResponse = new BaseApiResponse<>(
            HttpStatus.NOT_FOUND.value(), // code
            errorResponse, // data
            "Tài nguyên không tồn tại" // message
        );

        // trả về ResponseEntity
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    // xử lý lỗi validation khi dữ liệu trong @RequestBody không hợp lệ
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseApiResponse<ErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "Validation failed",
            request.getRequestURI(),
            errors
        );

        BaseApiResponse<ErrorResponse> apiResponse = new BaseApiResponse<>(
            HttpStatus.BAD_REQUEST.value(),
            errorResponse,
            "Dữ liệu đầu vào không hợp lệ"
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<BaseApiResponse<ErrorResponse>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
        );

        BaseApiResponse<ErrorResponse> apiResponse = new BaseApiResponse<>(
            HttpStatus.CONFLICT.value(),
            errorResponse,
            "Xung đột dữ liệu khi tạo tài khoản"
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    // xử lý lỗi khác (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseApiResponse<ErrorResponse>> handleGeneralException(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
        );

        BaseApiResponse<ErrorResponse> apiResponse = new BaseApiResponse<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            errorResponse,
            "Đã xảy ra lỗi không mong muốn"
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
