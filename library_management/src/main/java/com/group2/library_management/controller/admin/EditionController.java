package com.group2.library_management.controller.admin;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group2.library_management.service.EditionService;

import lombok.RequiredArgsConstructor;
import com.group2.library_management.dto.request.UpdateEditionRequest;
import com.group2.library_management.dto.response.EditionUpdateResponse;
import com.group2.library_management.service.PublisherService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller("adminEditionController")
@RequestMapping("/admin/editions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
// commented because we have not implemented security yet
public class EditionController {

    private final EditionService editionService;
    private final PublisherService publisherService;

    private final MessageSource messageSource;

    private static final String VIEW_EDITION_EDIT = "admin/edition/edit";
    private static final String REDIRECT_TO_BOOKS_LIST = "redirect:/admin/books";
    private static final String REDIRECT_TO_EDITION_EDIT = "redirect:/admin/editions/%d/edit";

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model) {

        EditionUpdateResponse editionResponse = editionService.findEditionForUpdate(id);

        model.addAttribute("edition", editionResponse); 
        model.addAttribute("publishers", publisherService.findAll()); // Add publishers list to model
        model.addAttribute("activeMenu", "books");
        return VIEW_EDITION_EDIT; // Return to edit page
    }

    // Handle update form submission
    @PutMapping("/{id}")
    public String processEditionUpdate(@PathVariable Integer id,
                                       @Valid @ModelAttribute("edition") UpdateEditionRequest request,
                                       BindingResult bindingResult,
                                       @RequestParam("coverImageFile") MultipartFile coverImageFile,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {

        // Validation failed
        if (bindingResult.hasErrors()) {
            EditionUpdateResponse originalData = editionService.findEditionForUpdate(id);

            EditionUpdateResponse responseForView = new EditionUpdateResponse(
                id,
                request.getTitle(), 
                request.getIsbn(),
                request.getPublisherId(),
                request.getPublicationDate(),
                request.getPageNumber(),
                request.getLanguage(),
                request.getFormat(),
                originalData.coverImageUrl(),
                originalData.initialQuantity(),
                originalData.availableQuantity(),
                request.getVersion()
            );
            model.addAttribute("edition", responseForView);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "edition", bindingResult);
            model.addAttribute("publishers", publisherService.findAll());
            model.addAttribute("activeMenu", "books");
            return VIEW_EDITION_EDIT; // Return to edit page
        }

        editionService.updateEdition(id, request, coverImageFile);
        
        redirectAttributes.addFlashAttribute(
            "successMessage",
            messageSource.getMessage("admin.editions.message.update_success", null, LocaleContextHolder.getLocale())
        );
        return REDIRECT_TO_BOOKS_LIST;
    }

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
