package com.group2.library_management.service;

import com.group2.library_management.entity.enums.BookFormat;
import com.group2.library_management.entity.enums.BookStatus;
import com.group2.library_management.entity.enums.BorrowingStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * This service is responsible for providing display names
 * for Enum values in the application.
 * The bean name "enumDisplayService" is used to call directly from Thymeleaf templates.
 */
@Service("enumDisplayService")
public class EnumDisplayService {

    private static final Map<BorrowingStatus, String> BORROWING_STATUS_NAMES = Map.ofEntries(
            Map.entry(BorrowingStatus.PENDING, "Chờ phê duyệt"),
            Map.entry(BorrowingStatus.APPROVED, "Đã phê duyệt"),
            Map.entry(BorrowingStatus.BORROWED, "Đang mượn"),
            Map.entry(BorrowingStatus.REJECTED, "Đã từ chối"),
            Map.entry(BorrowingStatus.RETURNED, "Đã trả"),
            Map.entry(BorrowingStatus.OVERDUE, "Quá hạn"),
            Map.entry(BorrowingStatus.LOST_REPORTED, "Báo mất"),
            Map.entry(BorrowingStatus.CANCELLED, "Đã hủy")
    );

    private static final Map<BookStatus, String> BOOK_STATUS_NAMES = Map.ofEntries(
            Map.entry(BookStatus.AVAILABLE, "Sẵn có"),
            Map.entry(BookStatus.BORROWED, "Đang được mượn"),
            Map.entry(BookStatus.RESERVED, "Đã đặt trước"),
            Map.entry(BookStatus.LOST, "Báo mất"),
            Map.entry(BookStatus.DAMAGED, "Hư hỏng"),
            Map.entry(BookStatus.REPAIRING, "Đang sửa chữa"),
            Map.entry(BookStatus.ARCHIVED, "Lưu kho")
    );

    /**
     * Get display name for BorrowingStatus.
     * @param status The status to get name for.
     * @return Vietnamese display name, or Enum name (e.g., "PENDING") if not found.
     */
    public String getBorrowingStatusDisplayName(BorrowingStatus status) {
        return BORROWING_STATUS_NAMES.getOrDefault(status, status.name());
    }

    /**
     * Get display name for BookStatus.
     * @param status The status to get name for.
     * @return Vietnamese display name, or Enum name (e.g., "AVAILABLE") if not found.
     */
    public String getBookStatusDisplayName(BookStatus status) {
        return BOOK_STATUS_NAMES.getOrDefault(status, status.name());
    }

    // Using Map to store display names for easy expansion
    private static final Map<BookFormat, String> BOOK_FORMAT_DISPLAY_NAMES = Map.of(
        BookFormat.HARDCOVER, "Bìa cứng",
        BookFormat.SOFTCOVER, "Bìa mềm",
        BookFormat.EBOOK, "Sách điện tử"
    );

    /** Get display name for a specific BookFormat
     * @param format Enum BookFormat
     * @return String name displayed
     */
    public String getDisplayName(BookFormat format) {
        // enum is null
        if (format == null) {
            return "Không xác định";
        }

        // getOrDefault is a safe way to retrieve values from the Map
        // It will return the default value, which is the name of the enum (e.g., "HARDCOVER")
        // instead of throwing a NullPointerException.
        return BOOK_FORMAT_DISPLAY_NAMES.getOrDefault(format, format.name());
    }
}
