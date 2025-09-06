package com.mpt.journal.service;

import com.mpt.journal.entity.ManufacturerEntity;
import java.util.List;

public interface ManufacturerService {
    List<ManufacturerEntity> getAll();
    ManufacturerEntity getById(Long id);
    void save(ManufacturerEntity manufacturer);
    void delete(Long id);
    List<ManufacturerEntity> search(String type, String value);
}
