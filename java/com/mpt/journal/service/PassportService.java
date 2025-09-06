package com.mpt.journal.service;

import com.mpt.journal.entity.PassportEntity;
import java.util.List;

public interface PassportService {
    List<PassportEntity> getAll();
    PassportEntity getById(Long id);
    void save(PassportEntity passport);
    void delete(Long id);
    List<PassportEntity> search(String type, String value);
}