package com.group2.library_management.service;

import com.group2.library_management.dto.request.BorrowingRequest;
import com.group2.library_management.dto.response.BorrowingRequestResponse;
import com.group2.library_management.dto.response.BorrowingReceiptResponse;

public interface BorrowingRequestService {
    BorrowingRequestResponse createBorrowingRequestFromCart(BorrowingRequest request);

    /**
     * Hủy một yêu cầu mượn sách của người dùng hiện tại.
     * 
     * @param receiptId ID của phiếu mượn cần hủy.
     * @return DTO chi tiết của phiếu mượn sau khi đã được cập nhật trạng thái.
     */
    BorrowingReceiptResponse cancelBorrowingRequest(Integer receiptId);
}
