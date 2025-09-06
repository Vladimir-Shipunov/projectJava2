package com.mpt.journal.service;

import com.mpt.journal.entity.CartItem;
import com.mpt.journal.entity.ProductEntity;
import com.mpt.journal.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartService {
    
    List<CartItem> getUserCart(UserEntity user);
    
    CartItem addToCart(UserEntity user, ProductEntity product, int quantity);
    
    void updateCartItemQuantity(UserEntity user, Long productId, int quantity);
    
    void removeFromCart(UserEntity user, Long productId);
    
    void clearCart(UserEntity user);
    
    int getCartItemCount(UserEntity user);
    
    double getCartTotal(UserEntity user);
    
    @Transactional
    void mergeCart(UserEntity user, List<CartItem> temporaryCart);
}