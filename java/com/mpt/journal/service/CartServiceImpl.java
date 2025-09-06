package com.mpt.journal.service;

import com.mpt.journal.entity.CartItem;
import com.mpt.journal.entity.ProductEntity;
import com.mpt.journal.entity.UserEntity;
import com.mpt.journal.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Override
    public List<CartItem> getUserCart(UserEntity user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public CartItem addToCart(UserEntity user, ProductEntity product, int quantity) {
        Optional<CartItem> existingItem = cartRepository.findByUserAndProductId(user, product.getId());
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartRepository.save(item);
        } else {
            CartItem newItem = new CartItem(user, product, quantity);
            return cartRepository.save(newItem);
        }
    }

    @Override
    public void updateCartItemQuantity(UserEntity user, Long productId, int quantity) {
        if (quantity <= 0) {
            removeFromCart(user, productId);
            return;
        }
        
        Optional<CartItem> existingItem = cartRepository.findByUserAndProductId(user, productId);
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(quantity);
            cartRepository.save(item);
        }
    }

    @Override
    public void removeFromCart(UserEntity user, Long productId) {
        cartRepository.deleteByUserAndProductId(user, productId);
    }

    @Override
    public void clearCart(UserEntity user) {
        cartRepository.deleteByUser(user);
    }

    @Override
    public int getCartItemCount(UserEntity user) {
        return cartRepository.countByUser(user);
    }

    @Override
    public double getCartTotal(UserEntity user) {
        List<CartItem> cartItems = getUserCart(user);
        return cartItems.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    @Override
    public void mergeCart(UserEntity user, List<CartItem> temporaryCart) {
        for (CartItem tempItem : temporaryCart) {
            addToCart(user, tempItem.getProduct(), tempItem.getQuantity());
        }
    }
}