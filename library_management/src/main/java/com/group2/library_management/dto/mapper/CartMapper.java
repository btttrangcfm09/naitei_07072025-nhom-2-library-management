package com.group2.library_management.dto.mapper;

import com.group2.library_management.dto.response.CartItemResponse;
import com.group2.library_management.dto.response.CartResponse;
import com.group2.library_management.entity.Cart;
import com.group2.library_management.entity.CartItem;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {BookMapper.class} 
)
public interface CartMapper {

    /**
     * Chuyển đổi Cart entity thành CartResponse DTO.
     *
     * @param cart Entity giỏ hàng.
     * @return DTO chứa thông tin chi tiết giỏ hàng.
     */
    default CartResponse toCartResponse(Cart cart) {
        if (cart == null) {
            return new CartResponse(null, Collections.emptyList(), 0, 0);
        }

        Set<CartItem> items = cart.getItems();
        if (items == null || items.isEmpty()) {
            return new CartResponse(cart.getUser().getId(), Collections.emptyList(), 0, 0);
        }

        List<CartItemResponse> itemResponses = items.stream()
                .map(this::toCartItemResponse) 
                .collect(Collectors.toList());
        
        int totalUniqueItems = items.size();
        int totalItems = items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        return new CartResponse(cart.getUser().getId(), itemResponses, totalUniqueItems, totalItems);
    }

    @Mapping(source = "id", target = "cartItemId")
    @Mapping(source = "edition.id", target = "editionId")
    @Mapping(source = "edition.title", target = "title")
    @Mapping(source = "edition.coverImageUrl", target = "coverImageUrl")
    @Mapping(source = "edition.book.authorBooks", target = "authors", qualifiedByName = "authorBooksToAuthorNames")
    @Mapping(source = "edition.book.bookGenres", target = "genres", qualifiedByName = "bookGenresToGenreNames")
    @Mapping(source = "edition.language", target = "language")
    @Mapping(source = "edition.format", target = "format")
    CartItemResponse toCartItemResponse(CartItem cartItem);
}
