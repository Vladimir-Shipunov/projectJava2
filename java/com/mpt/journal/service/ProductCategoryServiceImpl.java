package com.mpt.journal.service;

import com.mpt.journal.entity.ProductCategoryEntity;
import com.mpt.journal.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Override
    public List<ProductCategoryEntity> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public ProductCategoryEntity getById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ProductCategoryEntity category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<ProductCategoryEntity> search(String type, String value) {
        if (value == null || value.trim().isEmpty()) {
            return getAll();
        }
        
        switch (type.toLowerCase()) {
            case "name":
                return categoryRepository.findByNameContainingIgnoreCase(value);
            case "description":
                return categoryRepository.findByDescriptionContainingIgnoreCase(value);
            default:
                return getAll();
        }
    }
}