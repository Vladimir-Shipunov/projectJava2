package com.mpt.journal.repository;

import com.mpt.journal.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
    List<DeliveryEntity> findByTypeContainingIgnoreCase(String type);
    List<DeliveryEntity> findByDescriptionContainingIgnoreCase(String description);
    List<DeliveryEntity> findByCost(double cost);
    List<DeliveryEntity> findByDeliveryTimeContainingIgnoreCase(String deliveryTime);
}
