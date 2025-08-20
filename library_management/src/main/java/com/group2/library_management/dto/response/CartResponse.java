package com.group2.library_management.dto.response;

import java.util.List;

public record CartResponse(
    Integer userId,
    List<CartItemResponse> items,
    int totalUniqueItems, 
    int totalItems      
) {}
