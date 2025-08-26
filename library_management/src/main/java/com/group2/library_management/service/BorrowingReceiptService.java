package com.group2.library_management.service;

import com.group2.library_management.dto.request.UpdateBorrowingDetailRequest; // Thêm import này
import com.group2.library_management.dto.response.BorrowingReceiptResponse;
import com.group2.library_management.entity.enums.BorrowingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BorrowingReceiptService {
    Page<BorrowingReceiptResponse> getAllBorrowingRequests(String keyword, BorrowingStatus status, Pageable pageable);

    BorrowingReceiptResponse getBorrowingRequestById(Integer id);

    void approveBorrowingRequest(Integer id);

    void rejectBorrowingRequest(Integer id, String rejectedReason);

    void returnBook(Integer id);

    void updateBorrowingDetails(Integer receiptId, List<UpdateBorrowingDetailRequest> requests);

    /**
     * Check and update overdue status for a specific borrowing receipt
     * @param receiptId the ID of the borrowing receipt to check
     * @return true if the status was updated to OVERDUE, false otherwise
     */
    boolean checkAndUpdateOverdueStatus(Integer receiptId);
}
