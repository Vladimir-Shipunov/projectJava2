package com.mpt.journal.service;

import com.mpt.journal.entity.DeliveryEntity;
import java.util.List;

public interface DeliveryService {
    List<DeliveryEntity> getAll();
    DeliveryEntity getById(Long id);
    void save(DeliveryEntity delivery);
    void delete(Long id);
    List<DeliveryEntity> search(String type, String value);
}