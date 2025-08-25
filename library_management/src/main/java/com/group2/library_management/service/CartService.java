package com.group2.library_management.service;

import com.group2.library_management.dto.request.AddToCartRequest;
import com.group2.library_management.dto.response.CartResponse;
import com.group2.library_management.dto.response.CartUpdateResponse;

public interface CartService {
    CartUpdateResponse addToCart(AddToCartRequest request);

    CartResponse viewCart();
    
    /**
     * Xóa một item khỏi giỏ hàng và trả về toàn bộ giỏ hàng đã được cập nhật.
     * 
     * @param cartItemId ID của cart item cần xóa.
     * @return DTO chứa thông tin chi tiết của giỏ hàng sau khi cập nhật.
     */
    CartResponse removeItemFromCart(Integer cartItemId);

    /**
     * Xóa tất cả các item khỏi giỏ hàng của người dùng hiện tại.
     * 
     * @return Trạng thái giỏ hàng sau khi đã được làm sạch.
     */
    CartResponse clearCart();
}
