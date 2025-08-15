package com.group2.library_management.controller.admin;

import com.group2.library_management.dto.response.BorrowingReceiptResponse;
import com.group2.library_management.entity.enums.BookStatus;
import com.group2.library_management.entity.enums.BorrowingStatus;
import com.group2.library_management.service.BorrowingReceiptService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/borrow-requests")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingReceiptService borrowingReceiptService;

    @GetMapping
    public String showBorrowingRequestList(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "status", required = false) Integer statusValue,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        BorrowingStatus statusEnum = (statusValue != null) ?
                BorrowingStatus.fromValue(statusValue) : null;
        Page<BorrowingReceiptResponse> borrowingPage =
                borrowingReceiptService.getAllBorrowingRequests(keyword, statusEnum, pageable);

        model.addAttribute("borrowingPage", borrowingPage);
        model.addAttribute("allStatuses", BorrowingStatus.values());

        model.addAttribute("keyword", keyword);
        model.addAttribute("currentStatus", statusValue);

        return "admin/borrowing/list";
    }

    @GetMapping("/{id}")
    public String showBorrowingRequestDetail(
            @PathVariable("id") Integer id,
            Model model
    ) {
        // If the service throws EntityNotFoundException, GlobalExceptionHandler will automatically catch it.
        BorrowingReceiptResponse receipt = borrowingReceiptService.getBorrowingRequestById(id);
        model.addAttribute("receipt", receipt);
        return "admin/borrowing/detail";
    }
}
