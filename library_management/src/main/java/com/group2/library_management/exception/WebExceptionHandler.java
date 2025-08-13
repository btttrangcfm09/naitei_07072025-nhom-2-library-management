package com.group2.library_management.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = "com.group2.library_management.controller.admin")
public class WebExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleWebException(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error"); // Tên file templates/error.html
        return mav;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleNotFound(HttpServletRequest req, ResourceNotFoundException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", ex.getMessage());
        mav.setViewName("404");
        return mav;
    }
}
