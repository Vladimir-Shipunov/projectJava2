package com.mpt.journal.repository;

import com.mpt.journal.entity.CartItem;
import com.mpt.journal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findByUser(UserEntity user);
    
    Optional<CartItem> findByUserAndProductId(UserEntity user, Long productId);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.user = :user")
    void deleteByUser(@Param("user") UserEntity user);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.user = :user AND c.product.id = :productId")
    void deleteByUserAndProductId(@Param("user") UserEntity user, @Param("productId") Long productId);
    
    int countByUser(UserEntity user);
}