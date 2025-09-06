package com.mpt.journal.repository;

import com.mpt.journal.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId")
    List<OrderItem> findItemsByOrderId(@Param("orderId") Long orderId);
    
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.product.id = :productId")
    Long getTotalSoldQuantity(@Param("productId") Long productId);
}