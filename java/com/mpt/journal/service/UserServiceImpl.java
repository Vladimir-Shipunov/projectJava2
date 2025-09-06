package com.mpt.journal.service;

import com.mpt.journal.entity.UserEntity;
import com.mpt.journal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void save(UserEntity user) {
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
        logger.info("Пользователь сохранен: {}", user.getEmail());
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        logger.info("Пользователь удален: ID={}", id);
    }

    @Override
    public List<UserEntity> search(String type, String value) {
        if (value == null || value.trim().isEmpty()) {
            return getAll();
        }
        
        switch (type.toLowerCase()) {
            case "name":
                return userRepository.findByNameContainingIgnoreCase(value);
            case "email":
                return userRepository.findByEmailContainingIgnoreCase(value);
            case "username":
                Optional<UserEntity> user = userRepository.findByUsername(value);
                return user.map(List::of).orElse(List.of());
            default:
                return getAll();
        }
    }

    @Override
    public UserEntity registerNewUser(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}