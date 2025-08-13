package com.group2.library_management.controller.admin;

import com.group2.library_management.dto.response.BorrowingReceiptResponse;
import com.group2.library_management.entity.enums.BorrowingStatus;
import com.group2.library_management.service.BorrowingReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/admin/borrow-requests")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingReceiptService borrowingReceiptService;

    private static final Map<BorrowingStatus, String> STATUS_DISPLAY_NAMES = Map.ofEntries(
            Map.entry(BorrowingStatus.PENDING, "Chờ phê duyệt"),
            Map.entry(BorrowingStatus.APPROVED, "Đã phê duyệt"),
            Map.entry(BorrowingStatus.BORROWED, "Đang mượn"),
            Map.entry(BorrowingStatus.REJECTED, "Đã từ chối"),
            Map.entry(BorrowingStatus.RETURNED, "Đã trả"),
            Map.entry(BorrowingStatus.OVERDUE, "Quá hạn"),
            Map.entry(BorrowingStatus.LOST_REPORTED, "Báo mất"),
            Map.entry(BorrowingStatus.CANCELLED, "Đã hủy")
    );


    @GetMapping
    public String showBorrowingRequestList(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "status", required = false) Integer statusValue,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        BorrowingStatus statusEnum = (statusValue != null) ? BorrowingStatus.fromValue(statusValue) : null;

        Page<BorrowingReceiptResponse> borrowingPage = borrowingReceiptService.getAllBorrowingRequests(keyword, statusEnum, pageable);

        model.addAttribute("borrowingPage", borrowingPage);
        model.addAttribute("allStatuses", BorrowingStatus.values());
        model.addAttribute("statusDisplayNames", STATUS_DISPLAY_NAMES);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentStatus", statusValue);

        return "admin/borrowing/list";
    }
}
