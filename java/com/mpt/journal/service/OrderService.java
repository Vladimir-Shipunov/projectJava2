package com.mpt.journal.service;

import com.mpt.journal.entity.OrderEntity;
import java.util.List;

public interface OrderService {
    List<OrderEntity> getAll();
    OrderEntity getById(Long id);
    void save(OrderEntity order);
    void delete(Long id);
    List<OrderEntity> search(String type, String value);
    List<OrderEntity> getUserOrders(Long userId);
    List<OrderEntity> getOrdersByStatus(String status);
}
