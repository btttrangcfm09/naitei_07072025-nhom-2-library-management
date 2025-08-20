package com.group2.library_management.service;

import com.group2.library_management.dto.request.AddToCartRequest;
import com.group2.library_management.dto.response.CartUpdateResponse;

public interface CartService {
    CartUpdateResponse addToCart(AddToCartRequest request);
}
