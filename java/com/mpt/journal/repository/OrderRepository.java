package com.mpt.journal.repository;

import com.mpt.journal.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustomerNameContainingIgnoreCase(String customerName);
    List<OrderEntity> findByPhoneContainingIgnoreCase(String phone);
    List<OrderEntity> findByDeliveryAddressContainingIgnoreCase(String deliveryAddress);
    List<OrderEntity> findByStatusContainingIgnoreCase(String status);
    List<OrderEntity> findByTotal(Double total);
    
    // Новые методы
    List<OrderEntity> findByUserId(Long userId);
    List<OrderEntity> findByStatus(String status);
    
    @Query("SELECT o FROM OrderEntity o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    List<OrderEntity> findByCreatedAtBetween(@Param("startDate") java.time.LocalDateTime startDate, 
                                           @Param("endDate") java.time.LocalDateTime endDate);
    
    @Query("SELECT o FROM OrderEntity o ORDER BY o.createdAt DESC")
    List<OrderEntity> findAllOrderByCreatedAtDesc();
    
    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.status = :status")
    Long countByStatus(@Param("status") String status);
}