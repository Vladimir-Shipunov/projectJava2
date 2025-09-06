package com.mpt.journal.service;

import com.mpt.journal.entity.RoleEntity;
import java.util.List;

public interface RoleService {
    List<RoleEntity> getAll();
    RoleEntity getById(Long id);
    void save(RoleEntity role);
    void delete(Long id);
    List<RoleEntity> search(String type, String value);
    
    // Добавьте этот метод
    RoleEntity findByName(String name);
}