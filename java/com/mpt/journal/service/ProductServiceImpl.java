package com.mpt.journal.service;

import com.mpt.journal.entity.ProductEntity;
import com.mpt.journal.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ProductEntity product) {
        productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductEntity> search(String type, String value) {
        if (value == null || value.trim().isEmpty()) {
            return getAll();
        }
        
        switch (type.toLowerCase()) {
            case "name":
                return productRepository.findByNameContainingIgnoreCase(value);
            case "description":
                return productRepository.findByDescriptionContainingIgnoreCase(value);
            case "price":
                try {
                    double price = Double.parseDouble(value);
                    return productRepository.findByPrice(price);
                } catch (NumberFormatException e) {
                    return Collections.emptyList();
                }
            default:
                return getAll();
        }
    }
      @Override
public List<ProductEntity> getProductsByCategory(Long categoryId) {
    return productRepository.findByCategoryId(categoryId);
}

@Override
public List<ProductEntity> getProductsByManufacturer(Long manufacturerId) {
    return productRepository.findByManufacturerId(manufacturerId);
}

@Override
public List<ProductEntity> getFeaturedProducts() {
    return productRepository.findTop8ByOrderByCreatedAtDesc();
}

@Override
public List<ProductEntity> searchProducts(String query) {
    if (query == null || query.trim().isEmpty()) {
        return getAll();
    }
    return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
}
}