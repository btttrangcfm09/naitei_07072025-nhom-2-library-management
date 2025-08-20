package com.group2.library_management.controller.api;

import com.group2.library_management.dto.request.AddToCartRequest;
import com.group2.library_management.dto.response.BaseApiResponse;
import com.group2.library_management.dto.response.CartUpdateResponse;
import com.group2.library_management.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final MessageSource messageSource;

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    @PostMapping("/add")
    public ResponseEntity<BaseApiResponse<CartUpdateResponse>> addToCart(@Valid @RequestBody AddToCartRequest request) {
        CartUpdateResponse cartUpdateResponse = cartService.addToCart(request);
        
        String successMessage = getMessage("success.cart.add_item");
        BaseApiResponse<CartUpdateResponse> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                cartUpdateResponse,
                successMessage
        );
        
        return ResponseEntity.ok(response);
    }
}
