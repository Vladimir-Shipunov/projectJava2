package com.mpt.journal.service;

import com.mpt.journal.entity.DeliveryEntity;
import com.mpt.journal.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public List<DeliveryEntity> getAll() {
        return deliveryRepository.findAll();
    }

    @Override
    public DeliveryEntity getById(Long id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    @Override
    public void save(DeliveryEntity delivery) {
        deliveryRepository.save(delivery);
    }

    @Override
    public void delete(Long id) {
        deliveryRepository.deleteById(id);
    }

    @Override
    public List<DeliveryEntity> search(String type, String value) {
        if (value == null || value.trim().isEmpty()) {
            return getAll();
        }
        
        switch (type.toLowerCase()) {
            case "type":
                return deliveryRepository.findByTypeContainingIgnoreCase(value);
            case "description":
                return deliveryRepository.findByDescriptionContainingIgnoreCase(value);
            case "cost":
                try {
                    double cost = Double.parseDouble(value);
                    return deliveryRepository.findByCost(cost);
                } catch (NumberFormatException e) {
                    return Collections.emptyList();
                }
            case "deliverytime":
                return deliveryRepository.findByDeliveryTimeContainingIgnoreCase(value);
            default:
                return getAll();
        }
    }
}