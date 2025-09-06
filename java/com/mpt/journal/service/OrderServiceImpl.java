package com.mpt.journal.service;

import com.mpt.journal.entity.OrderEntity;
import com.mpt.journal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderEntity> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public OrderEntity getById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public void save(OrderEntity order) {
        orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderEntity> search(String type, String value) {
        if (value == null || value.trim().isEmpty()) {
            return getAll();
        }
        
        switch (type.toLowerCase()) {
            case "customername":
                return orderRepository.findByCustomerNameContainingIgnoreCase(value);
            case "phone":
                return orderRepository.findByPhoneContainingIgnoreCase(value);
            case "deliveryaddress":
                return orderRepository.findByDeliveryAddressContainingIgnoreCase(value);
            case "status":
                return orderRepository.findByStatusContainingIgnoreCase(value);
            case "total":
                try {
                    double total = Double.parseDouble(value);
                    return orderRepository.findByTotal(total);
                } catch (NumberFormatException e) {
                    return Collections.emptyList();
                }
            default:
                return getAll();
        }
    }

    @Override
    public List<OrderEntity> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<OrderEntity> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
}