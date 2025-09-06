package com.mpt.journal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpt.journal.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategoryId(Long categoryId);
    List<ProductEntity> findByManufacturerId(Long manufacturerId);
}
