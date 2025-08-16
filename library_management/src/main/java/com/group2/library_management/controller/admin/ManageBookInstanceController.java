package com.group2.library_management.controller.admin;

import java.io.IOException;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import com.group2.library_management.dto.response.EditionListResponse;
import com.group2.library_management.exception.ImportValidationException;
import com.group2.library_management.service.EditionService;
import com.group2.library_management.service.impl.BookImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Controller
@RequestMapping("/admin/bookinstances")
@RequiredArgsConstructor
public class ManageBookInstanceController {
    private final BookImportService bookImportService;
    private final MessageSource messageSource;
    private final EditionService editionService;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 100;
    @GetMapping
    public String showEditionList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            Model model) {
        // --- Input Validation ---
        // Ensure the page number is not less than 1.
        int validatedPage = Math.max(1, page);

        // Ensure the page size is within a reasonable range (e.g., 1 to 100).
        int validatedSize = (size > 0 && size <= MAX_PAGE_SIZE) ? size : DEFAULT_PAGE_SIZE;

        Pageable pageable = PageRequest.of(validatedPage - 1, validatedSize, Sort.by("title").ascending());
        // Call the service layer to fetch the paginated list of
        Page<EditionListResponse> editionPage = editionService.getAllEditions(pageable);
        // Add the retrieved data and search parameters to the model for the view
        model.addAttribute("editionPage", editionPage);
        return "admin/bookinstances/list";
    }

    @PostMapping("/import")
    public String importBookInstances(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
            throws ImportValidationException, IOException {
        Locale locale = LocaleContextHolder.getLocale();
        bookImportService.importFromFile(file);
        // Success message
        String successMessage = messageSource.getMessage("upload.file.success", null, locale);
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/admin/bookinstances";
    }
}
