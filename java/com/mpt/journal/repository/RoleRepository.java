package com.mpt.journal.repository;

import com.mpt.journal.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
    List<RoleEntity> findByNameContainingIgnoreCase(String name);
}