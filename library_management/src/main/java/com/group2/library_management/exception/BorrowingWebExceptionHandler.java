package com.group2.library_management.exception;

import com.group2.library_management.common.constants.UrlConstants;
import com.group2.library_management.controller.admin.BorrowingController;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@ControllerAdvice(assignableTypes = {BorrowingController.class})
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class BorrowingWebExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({EntityNotFoundException.class, IllegalStateException.class})
    public String handleBusinessException(Exception ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String requestURI = request.getRequestURI();
        log.warn("Lỗi nghiệp vụ trên WEB request [{}]: {}", requestURI, ex.getMessage());

        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

        Object pathVariablesAttr = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVariablesAttr instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, String> pathVariables = (Map<String, String>) pathVariablesAttr;
            String id = pathVariables.get("id");
            if (id != null && !id.isEmpty()) {
                return "redirect:" + UrlConstants.BORROW_REQUESTS_DETAIL.replace("{id}", id);
            }
        }

        return UrlConstants.REDIRECT_BORROW_REQUESTS_LIST;
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String requestURI = request.getRequestURI();
        log.error("Lỗi hệ thống trên WEB request [{}]: {}", requestURI, ex.toString(), ex);

        String errorMessage = messageSource.getMessage(
                "admin.borrowing.error.generic_system_error",
                null,
                LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

        return UrlConstants.REDIRECT_BORROW_REQUESTS_LIST;
    }
}
