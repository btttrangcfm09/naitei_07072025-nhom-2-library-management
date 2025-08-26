package com.group2.library_management.controller.api;

import com.group2.library_management.common.constants.Endpoints;
import com.group2.library_management.dto.request.BorrowingHistoryRequestParams;
import com.group2.library_management.dto.response.BaseApiResponse;
import com.group2.library_management.dto.response.BorrowingHistoryResponse;
import com.group2.library_management.dto.response.PaginatedResponse;
import com.group2.library_management.service.BorrowingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;

@RestController("apiBorrowingHistoryController")
@RequestMapping(Endpoints.ApiV1.BorrowingHistory.BASE_URL)
@RequiredArgsConstructor
@Validated
public class BorrowingHistoryController {

    private final BorrowingHistoryService borrowingHistoryService;
    private final MessageSource messageSource;

    @GetMapping(Endpoints.ApiV1.BorrowingHistory.GET_LIST_ACTION)
    public ResponseEntity<BaseApiResponse<PaginatedResponse<BorrowingHistoryResponse>>> getMyBorrowingHistory(
            BorrowingHistoryRequestParams params) {

        Page<BorrowingHistoryResponse> historyPage = borrowingHistoryService.getMyBorrowingHistory(params);
        PaginatedResponse<BorrowingHistoryResponse> paginatedData = PaginatedResponse.fromPage(historyPage);

        String successMessage = messageSource.getMessage(
            "success.borrowing_history.get_list", 
            null,
            Locale.getDefault()
        );

        BaseApiResponse<PaginatedResponse<BorrowingHistoryResponse>> response = new BaseApiResponse<>(
            HttpStatus.OK.value(),
            paginatedData,
            successMessage
        );

        return ResponseEntity.ok(response);
    }
}
