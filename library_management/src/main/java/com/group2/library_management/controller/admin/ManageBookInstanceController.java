package com.group2.library_management.controller.admin;

import java.io.IOException;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.context.i18n.LocaleContextHolder; 
import com.group2.library_management.exception.ImportValidationException;
import com.group2.library_management.service.impl.BookImportService;

@Controller
@RequestMapping("/admin/bookinstances")
public class ManageBookInstanceController {
    private final BookImportService bookImportService;
    private final MessageSource messageSource;
    @Autowired
    public ManageBookInstanceController(BookImportService bookImportService, MessageSource messageSource) {
        this.bookImportService = bookImportService;
        this.messageSource = messageSource;
    }   
    @PostMapping("/import")
    public String importBookInstances(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws ImportValidationException, IOException{
        Locale locale = LocaleContextHolder.getLocale();
        bookImportService.importFromFile(file); 
        // Thông báo thành công
        String successMessage = messageSource.getMessage("upload.file.success", null, locale);
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/admin/bookinstances";
    }
}
