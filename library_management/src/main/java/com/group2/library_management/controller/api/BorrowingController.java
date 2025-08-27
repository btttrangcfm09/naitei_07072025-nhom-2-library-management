package com.group2.library_management.controller.api;

import com.group2.library_management.common.constants.Endpoints;
import com.group2.library_management.dto.request.BorrowingRequest;
import com.group2.library_management.dto.response.BaseApiResponse;
import com.group2.library_management.dto.response.BorrowingRequestResponse;
import com.group2.library_management.service.BorrowingRequestService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.group2.library_management.dto.response.BorrowingReceiptResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiBorrowingController")
@RequestMapping(Endpoints.ApiV1.Borrowings.BASE_URL)
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingRequestService borrowingRequestService;
    private final MessageSource messageSource;

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    @PostMapping(Endpoints.ApiV1.Borrowings.CREATE_REQUEST_ACTION)
    public ResponseEntity<BaseApiResponse<BorrowingRequestResponse>> createBorrowingRequest(@Valid @RequestBody BorrowingRequest request) {
        BorrowingRequestResponse responseData = borrowingRequestService.createBorrowingRequestFromCart(request);
        
        String successMessage = getMessage("success.borrowing.request_created");
        BaseApiResponse<BorrowingRequestResponse> response = new BaseApiResponse<>(
                HttpStatus.CREATED.value(),
                responseData,
                successMessage
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @DeleteMapping(Endpoints.ApiV1.Borrowings.CANCLE_REQUEST_ACTION)
    public ResponseEntity<BaseApiResponse<BorrowingReceiptResponse>> cancelBorrowingRequest(@PathVariable("id") Integer receiptId) {
        BorrowingReceiptResponse responseData = borrowingRequestService.cancelBorrowingRequest(receiptId);
        
        String successMessage = getMessage("success.borrowing.request_cancelled");
        BaseApiResponse<BorrowingReceiptResponse> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                responseData,
                successMessage
        );
         
        return ResponseEntity.ok(response);
    }
}
