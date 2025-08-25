package com.group2.library_management.controller.admin;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group2.library_management.service.EditionService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/editions")
@RequiredArgsConstructor
public class EditionController {
    
    private final EditionService editionService;

    private final MessageSource messageSource;
    
    @DeleteMapping("/{id}")
    public String processEditionDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        editionService.deleteEdition(id);
        
        String successMessage = messageSource.getMessage(
            "admin.editions.message.delete_success", 
            null, 
            LocaleContextHolder.getLocale()
        );
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        
        return "redirect:/admin/books";
    }
}
