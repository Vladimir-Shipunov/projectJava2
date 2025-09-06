package com.mpt.journal.service;

import com.mpt.journal.entity.ManufacturerEntity;
import com.mpt.journal.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Override
    public List<ManufacturerEntity> getAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public ManufacturerEntity getById(Long id) {
        return manufacturerRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ManufacturerEntity manufacturer) {
        manufacturerRepository.save(manufacturer);
    }

    @Override
    public void delete(Long id) {
        manufacturerRepository.deleteById(id);
    }

    @Override
    public List<ManufacturerEntity> search(String type, String value) {
        if (value == null || value.trim().isEmpty()) {
            return getAll();
        }
        
        switch (type.toLowerCase()) {
            case "name":
                return manufacturerRepository.findByNameContainingIgnoreCase(value);
            case "country":
                return manufacturerRepository.findByCountryContainingIgnoreCase(value);
            case "address":
                return manufacturerRepository.findByAddressContainingIgnoreCase(value);
            default:
                return getAll();
        }
    }
}