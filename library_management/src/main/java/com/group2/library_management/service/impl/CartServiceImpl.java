package com.group2.library_management.service.impl;

import com.group2.library_management.dto.mapper.CartMapper;
import com.group2.library_management.dto.request.AddToCartRequest;
import com.group2.library_management.dto.response.CartResponse;
import com.group2.library_management.dto.response.CartUpdateResponse;
import com.group2.library_management.entity.*;
import com.group2.library_management.exception.CartQuantityLimitException;
import com.group2.library_management.exception.CartTotalItemLimitException;
import com.group2.library_management.exception.ResourceNotFoundException;
import com.group2.library_management.repository.CartItemRepository;
import com.group2.library_management.repository.CartRepository;
import com.group2.library_management.repository.EditionRepository; 
import com.group2.library_management.service.AbstractBaseService;
import com.group2.library_management.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl extends AbstractBaseService implements CartService {

    private final CartRepository cartRepository;
    private final EditionRepository editionRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    private static final int MAX_QUANTITY_PER_EDITION = 2;
    private static final int MAX_TOTAL_ITEMS_IN_CART  = 5;

    @Override
    @Transactional
    public CartUpdateResponse addToCart(AddToCartRequest request) {
        // 1. Retrieve the currently logged-in user information.
        User currentUser = getCurrentUser();

        // 2. Find or create a new shopping cart for the user.
        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseGet(() -> createNewCart(currentUser));

        // 3. Check whether the total number of books in the cart after adding exceeds the maximum limit.
        int currentTotalQuantity = cart.getItems().stream()
                                       .mapToInt(CartItem::getQuantity)
                                       .sum();
        int newTotalQuantity = currentTotalQuantity + request.quantity();

        if (newTotalQuantity > MAX_TOTAL_ITEMS_IN_CART) {
            throw new CartTotalItemLimitException();
        }

        // 4. Find the book edition that the user wants to add.
        Edition edition = editionRepository.findById(request.editionId())
                .orElseThrow(() -> new ResourceNotFoundException());

        // 5. Check whether the book already exists in the cart.
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getEdition().getId().equals(request.editionId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + request.quantity();

            if (newQuantity > MAX_QUANTITY_PER_EDITION) {
                throw new CartQuantityLimitException();
            }

            existingItem.setQuantity(newQuantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setEdition(edition);
            newItem.setQuantity(request.quantity());
            cart.addItem(newItem);
        }

        cartRepository.save(cart);

        return new CartUpdateResponse(newTotalQuantity);
    }

    private Cart createNewCart(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse viewCart() {
        User currentUser = getCurrentUser();

        Cart cart = cartRepository.findById(currentUser.getId()).orElseGet(() -> createNewCart(currentUser));
        return cartMapper.toCartResponse(cart);
    }
    
    @Override
    @Transactional 
    public CartResponse removeItemFromCart(Integer cartItemId) {
        User currentUser = getCurrentUser();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException());

        Cart cart = cartItem.getCart();
        if (cart == null || cart.getUser() == null || !cart.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException();
        }

        cart.removeItem(cartItem);
        cartRepository.save(cart);

        return cartMapper.toCartResponse(cart);
    }
}
