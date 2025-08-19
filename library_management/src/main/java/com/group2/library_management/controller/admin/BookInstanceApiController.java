package com.group2.library_management.controller.admin;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group2.library_management.dto.response.BookInstanceResponse;
import com.group2.library_management.exception.BookInstanceNotFoundException;
import com.group2.library_management.exception.CannotDeleteResourceException;
import com.group2.library_management.service.BookInstanceService;

import lombok.RequiredArgsConstructor;

@RestController("AdminBookInstanceApiController")
@RequestMapping("/admin/bookinstances")
@RequiredArgsConstructor
public class BookInstanceApiController {
    private final BookInstanceService bookInstanceService;
    private final MessageSource messageSource;

    private String getMessage(String message){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, locale);
    }


    @GetMapping("/edition/{editionId}")
    public ResponseEntity<List<BookInstanceResponse>> getBookInstancesByEditionId(@PathVariable Integer editionId) {
        List<BookInstanceResponse> bookInstances = bookInstanceService.getBookInstancesByEditionId(editionId);
        return ResponseEntity.ok(bookInstances);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteBookInstance(@PathVariable Integer id)
            throws BookInstanceNotFoundException, CannotDeleteResourceException {
        bookInstanceService.deleteBookInstanceByBookInstanceId(id);
        return ResponseEntity.ok(Map.of("message", getMessage("admin.bookinstance.delete.success.message")));
    }
}
