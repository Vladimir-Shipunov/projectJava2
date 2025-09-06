package com.mpt.journal.service;

import com.mpt.journal.entity.PayEntity;
import java.util.List;

public interface PayService {
    List<PayEntity> getAll();
    PayEntity getById(Long id);
    void save(PayEntity pay);
    void delete(Long id);
    List<PayEntity> search(String type, String value);
}
