package com.mpt.journal.service;

import com.mpt.journal.entity.UserEntity;
import java.util.List;

public interface UserService {
    List<UserEntity> getAll();
    UserEntity getById(Long id);
    UserEntity getUserByUsername(String username);
    UserEntity getUserByEmail(String email);
    void save(UserEntity user);
    void delete(Long id);
    List<UserEntity> search(String type, String value);
    UserEntity registerNewUser(UserEntity user);
}