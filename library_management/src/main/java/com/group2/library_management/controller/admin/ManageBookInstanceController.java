package com.group2.library_management.controller.admin;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.PageRequest;

import com.group2.library_management.dto.request.BookInstanceSearchRequest;
import com.group2.library_management.dto.response.EditionListResponse;
import com.group2.library_management.entity.enums.BookStatus;
import com.group2.library_management.dto.mapper.BookInstanceMapper;
import com.group2.library_management.dto.request.bookinstance.UpdateBookInstanceRequest;
import com.group2.library_management.dto.response.BookInstanceEditResponse;
import com.group2.library_management.entity.BookInstance;
import com.group2.library_management.exception.ConcurrencyException;
import com.group2.library_management.exception.ImportValidationException;
import com.group2.library_management.exception.ResourceNotFoundException;
import com.group2.library_management.service.BookInstanceService;
import com.group2.library_management.service.EditionService;
import com.group2.library_management.service.impl.BookImportService;
import com.group2.library_management.util.ConstUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/admin/bookinstances")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
// commented because we have not implemented security yet
public class ManageBookInstanceController {
    
    // service
    private final BookImportService bookImportService;
    private final EditionService editionService;
    private final BookInstanceService bookInstanceService;

    // mapper
    private final BookInstanceMapper bookInstanceMapper;    

    // view
    private static final String VIEW_BOOKINSTANCE_EDIT = "admin/bookinstances/edit";
    private static final String REDIRECT_TO_BOOKINSTANCE_LIST = "redirect:/admin/bookinstances";
    private static final String REDIRECT_TO_BOOKINSTANCE_EDIT = "redirect:/admin/bookinstances/%s/edit";

    // message
    private final MessageSource messageSource;

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

    @GetMapping("/{id}/edit")
    public String showBookInstanceEditForm(@PathVariable Integer id, Model model) throws ResourceNotFoundException {
        BookInstance bookInstance = bookInstanceService.getBookInstanceById(id);
        BookInstanceEditResponse bookInstanceEditResponse = bookInstanceMapper.mapToBookInstanceEditResponse(bookInstance);
        UpdateBookInstanceRequest updateBookInstanceRequest = bookInstanceMapper.maptoUpdateBookInstanceRequest(bookInstance);
        model.addAttribute("bookInstance", bookInstanceEditResponse);
        model.addAttribute("bookInstanceRequest", updateBookInstanceRequest);
        return VIEW_BOOKINSTANCE_EDIT;
    }

    @PutMapping("/{id}")
    public String processBookInstanceUpdate(@PathVariable Integer id,
                                            @Valid @ModelAttribute("bookInstanceRequest") UpdateBookInstanceRequest request,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes,
                                            Model model) 
                                            throws IllegalArgumentException, ResourceNotFoundException, MethodArgumentNotValidException, ConcurrencyException {
        if(bindingResult.hasErrors()){
            BookInstance bookInstance = bookInstanceService.getBookInstanceById(id);
            BookInstanceEditResponse bookInstanceEditResponse = bookInstanceMapper.mapToBookInstanceEditResponse(bookInstance);
            model.addAttribute("bookInstance", bookInstanceEditResponse);
            redirectAttributes.addFlashAttribute(
                "errorMessage",
                bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList())
            );
            return String.format(REDIRECT_TO_BOOKINSTANCE_EDIT, id); 
        }

        bookInstanceService.updateBookInstance(id, request);
        redirectAttributes.addFlashAttribute(
            "successMessage",
            messageSource.getMessage("admin.bookinstances.message.update_success", new Object[] {request.barcode()}, LocaleContextHolder.getLocale())
        );
        return REDIRECT_TO_BOOKINSTANCE_LIST;
    }
}
