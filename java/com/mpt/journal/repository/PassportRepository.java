package com.mpt.journal.repository;

import com.mpt.journal.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PassportRepository extends JpaRepository<PassportEntity, Long> {
    List<PassportEntity> findByOwnerNameContainingIgnoreCase(String ownerName);
    List<PassportEntity> findByPassportNumberContainingIgnoreCase(String passportNumber);
    List<PassportEntity> findByIssueDateContainingIgnoreCase(String issueDate);
    List<PassportEntity> findByIssuedByContainingIgnoreCase(String issuedBy);
}
