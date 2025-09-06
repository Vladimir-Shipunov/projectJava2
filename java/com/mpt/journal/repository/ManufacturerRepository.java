package com.mpt.journal.repository;

import com.mpt.journal.entity.ManufacturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity, Long> {
    List<ManufacturerEntity> findByNameContainingIgnoreCase(String name);
    List<ManufacturerEntity> findByCountryContainingIgnoreCase(String country);
    List<ManufacturerEntity> findByAddressContainingIgnoreCase(String address);
}
