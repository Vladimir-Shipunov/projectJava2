package com.mpt.journal.service;

import com.mpt.journal.entity.ProductEntity;
import java.util.List;

public interface ProductService {
    List<ProductEntity> getAll();
    ProductEntity getById(Long id);
    void save(ProductEntity product);
    void delete(Long id);
    List<ProductEntity> search(String type, String value);
    
    // Новые методы
    List<ProductEntity> getProductsByCategory(Long categoryId);
    List<ProductEntity> getProductsByManufacturer(Long manufacturerId);
    List<ProductEntity> getFeaturedProducts();
    List<ProductEntity> searchProducts(String query);
}
