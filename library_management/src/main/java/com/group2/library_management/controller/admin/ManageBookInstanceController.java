package com.group2.library_management.controller.admin;

import java.io.IOException;
import java.time.LocalDate;
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

import com.group2.library_management.dto.request.BookInstanceSearchRequest;
import com.group2.library_management.dto.response.EditionListResponse;
import com.group2.library_management.entity.enums.BookStatus;
import com.group2.library_management.exception.ImportValidationException;
import com.group2.library_management.service.EditionService;
import com.group2.library_management.service.impl.BookImportService;
import com.group2.library_management.util.ConstUtil;

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

    public String getMessage(String message) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, locale);
    }

    @GetMapping
    public String showEditionList(
            BookInstanceSearchRequest searchRequest,
            Model model) throws IllegalArgumentException {

        // Ensure the page number is not less than 1.
        int validatedPage = Math.max(1, searchRequest.page());

        // Ensure the page size is within a reasonable range (e.g., 1 to 100).
        int validatedSize = (searchRequest.size() > 0 && searchRequest.size() <= ConstUtil.MAX_PAGE_SIZE) ? searchRequest.size() : ConstUtil.DEFAULT_PAGE_SIZE;

        Pageable pageable = PageRequest.of(validatedPage - 1, validatedSize, Sort.by("title").ascending());
        // Call the service layer to fetch the paginated list of

        BookStatus bookStatus = (searchRequest.statusValue() != null) ? BookStatus.fromValue(searchRequest.statusValue()) : null;
        Page<EditionListResponse> editionPage = editionService.searchEditionAndBookInstance(searchRequest.keyword(), searchRequest.filterBy(), bookStatus, searchRequest.fromDate(), searchRequest.toDate(), pageable);

        // valid fromDate and toDate
        if (searchRequest.fromDate() != null && searchRequest.toDate() != null && searchRequest.fromDate().isAfter(searchRequest.toDate())) {
            throw new IllegalArgumentException(getMessage("admin.bookinstance.valid.date"));
        }

        // Add the retrieved data and search parameters to the model for the view
        model.addAttribute("editionPage", editionPage);

        // Add parameter to save in form
        model.addAttribute("searchRequest", searchRequest);
        model.addAttribute("allStatus", BookStatus.values());
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
