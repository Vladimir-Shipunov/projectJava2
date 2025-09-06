package com.mpt.journal.service;

import com.mpt.journal.entity.RoleEntity;
import com.mpt.journal.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleEntity> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public RoleEntity getById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void save(RoleEntity role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<RoleEntity> search(String type, String value) {
        // Реализация поиска
        return getAll();
    }

    @Override
    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name);
    }
}