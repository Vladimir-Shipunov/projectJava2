package com.mpt.journal.repository;

import com.mpt.journal.entity.PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PayRepository extends JpaRepository<PayEntity, Long> {
    List<PayEntity> findByStatusContainingIgnoreCase(String status);
    List<PayEntity> findByPaymentTypeContainingIgnoreCase(String paymentType);
    List<PayEntity> findByAmount(double amount);
    List<PayEntity> findByPaymentDateContainingIgnoreCase(String paymentDate);
}
