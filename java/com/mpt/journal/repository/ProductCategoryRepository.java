package com.mpt.journal.repository;

import com.mpt.journal.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
    List<ProductCategoryEntity> findByNameContainingIgnoreCase(String name);
    List<ProductCategoryEntity> findByDescriptionContainingIgnoreCase(String description);
}
