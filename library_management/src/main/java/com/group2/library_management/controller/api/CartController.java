package com.group2.library_management.controller.api;

import com.group2.library_management.dto.request.AddToCartRequest;
import com.group2.library_management.dto.response.BaseApiResponse;
import com.group2.library_management.dto.response.CartResponse;
import com.group2.library_management.dto.response.CartUpdateResponse;
import com.group2.library_management.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    public ResponseEntity<BaseApiResponse<CartResponse>> viewCart() {
        CartResponse cartResponse = cartService.viewCart();
        
        String successMessage = getMessage("success.cart.view");
        BaseApiResponse<CartResponse> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                cartResponse,
                successMessage
        );
        
        return ResponseEntity.ok(response);
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

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<BaseApiResponse<CartResponse>> removeItemFromCart(@PathVariable Integer cartItemId) {
        CartResponse cartResponse = cartService.removeItemFromCart(cartItemId);
        
        String successMessage = getMessage("success.cart.remove_item");
        BaseApiResponse<CartResponse> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                cartResponse,
                successMessage
        );
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<BaseApiResponse<CartResponse>> clearCart() {
        CartResponse cartResponse = cartService.clearCart();
        
        String successMessage = getMessage("success.cart.clear");
        BaseApiResponse<CartResponse> response = new BaseApiResponse<>(
                HttpStatus.OK.value(),
                cartResponse,
                successMessage
        );
        
        return ResponseEntity.ok(response);
    }
}
