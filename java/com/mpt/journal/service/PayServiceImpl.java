package com.mpt.journal.service;

import com.mpt.journal.entity.PayEntity;
import com.mpt.journal.entity.ProductEntity;
import com.mpt.journal.repository.PayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private PayRepository payRepository;

    @Override
    public List<PayEntity> getAll() {
        return payRepository.findAll();
    }

    @Override
    public PayEntity getById(Long id) {
        return payRepository.findById(id).orElse(null);
    }

    @Override
    public void save(PayEntity pay) {
        payRepository.save(pay);
    }

    @Override
    public void delete(Long id) {
        payRepository.deleteById(id);
    }

    @Override
    public List<PayEntity> search(String type, String value) {
        switch (type) {
            case "id":
                try {
                    Long id = Long.parseLong(value);
                    return payRepository.findById(id).map(List::of).orElse(Collections.emptyList());
                } catch (NumberFormatException e) {
                    return Collections.emptyList();
                }
            case "paymentType":
                return payRepository.findByPaymentTypeContainingIgnoreCase(value);
            case "status":
                return payRepository.findByStatusContainingIgnoreCase(value);
            case "paymentDate":
                return payRepository.findByPaymentDateContainingIgnoreCase(value);
            case "amount":
                try {
                    double amount = Double.parseDouble(value);
                    return payRepository.findByAmount(amount);
                } catch (NumberFormatException e) {
                    return Collections.emptyList();
                }
            default:
                return payRepository.findAll();
        }
    }
  
}

