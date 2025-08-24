package com.group2.library_management.exception;

import com.group2.library_management.controller.admin.BorrowingController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {BorrowingController.class})
@RequiredArgsConstructor
@Slf4j
public class BorrowingApiExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<String> handleApiBusinessException(RuntimeException ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        log.warn("Lỗi nghiệp vụ trên API [{}]: {}", path, ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericApiException(Exception ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        log.error("Lỗi hệ thống trên API [{}]: {}", path, ex.toString(), ex);

        String errorMessage = messageSource.getMessage(
                "admin.borrowing.error.update_failed",
                null,
                LocaleContextHolder.getLocale());
        return ResponseEntity.internalServerError().body(errorMessage);
    }
}
