package com.mpt.journal.service;

import com.mpt.journal.entity.ProductCategoryEntity;
import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryEntity> getAll();
    ProductCategoryEntity getById(Long id);
    void save(ProductCategoryEntity category);
    void delete(Long id);
    List<ProductCategoryEntity> search(String type, String value);
}