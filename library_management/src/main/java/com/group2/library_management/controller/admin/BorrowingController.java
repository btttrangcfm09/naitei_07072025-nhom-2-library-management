package com.group2.library_management.controller.admin;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group2.library_management.dto.request.UpdateBorrowingDetailRequest;
import com.group2.library_management.dto.response.BorrowingReceiptResponse;
import com.group2.library_management.entity.enums.BorrowingStatus;
import com.group2.library_management.service.BorrowingReceiptService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/borrow-requests")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class BorrowingController {

    private final BorrowingReceiptService borrowingReceiptService;
    private final MessageSource messageSource;

    @GetMapping
    public String showBorrowingRequestList(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "status", required = false) Integer statusValue,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "20") int size, Model model) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        BorrowingStatus statusEnum = (statusValue != null) ? BorrowingStatus.fromValue(statusValue) : null;
        Page<BorrowingReceiptResponse> borrowingPage = borrowingReceiptService.getAllBorrowingRequests(keyword,
                statusEnum, pageable);

        model.addAttribute("borrowingPage", borrowingPage);
        model.addAttribute("allStatuses", BorrowingStatus.values());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentStatus", statusValue);
        return "admin/borrowing/list";
    }

    @GetMapping("/{id}")
    public String showBorrowingRequestDetail(@PathVariable("id") Integer id, Model model) {
        BorrowingReceiptResponse receipt = borrowingReceiptService.getBorrowingRequestById(id);
        model.addAttribute("receipt", receipt);
        return "admin/borrowing/detail";
    }

    @PutMapping("/{id}/approve")
    public String approveBorrowingRequest(@PathVariable("id") Integer id,
            RedirectAttributes redirectAttributes) {
        borrowingReceiptService.approveBorrowingRequest(id);

        String successMessage = messageSource.getMessage("admin.borrowing.success.approved",
                null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/admin/borrow-requests/" + id;
    }

    @PutMapping("/{id}/reject")
    public String rejectBorrowingRequest(@PathVariable("id") Integer id,
            @RequestParam("rejectedReason") String rejectedReason,
            RedirectAttributes redirectAttributes) {
        if (rejectedReason == null || rejectedReason.trim().isEmpty()) {
            String errorMessage = messageSource.getMessage("admin.borrowing.error.missing_reason", null,
                    LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/admin/borrow-requests/" + id;
        }

        borrowingReceiptService.rejectBorrowingRequest(id, rejectedReason.trim());

        String successMessage = messageSource.getMessage("admin.borrowing.success.rejected",
                null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/admin/borrow-requests/" + id;
    }

    /**
     * API endpoint to check and update overdue status
     */
    @PutMapping("/{id}/check-overdue")
    @ResponseBody
    public ResponseEntity<String> checkOverdueStatus(@PathVariable("id") Integer id) {
        boolean wasUpdated = borrowingReceiptService.checkAndUpdateOverdueStatus(id);
        return ResponseEntity.ok(wasUpdated ? "updated" : "no_change");
    }

    @PutMapping("/{id}/update-details")
    @ResponseBody
    public ResponseEntity<String> updateBorrowingDetails(@PathVariable("id") Integer id,
            @RequestBody @Valid List<UpdateBorrowingDetailRequest> requests) {
        borrowingReceiptService.updateBorrowingDetails(id, requests);
        return ResponseEntity.ok("success");
    }
}
