package com.mpt.journal.repository;

import com.mpt.journal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    
    // Добавляем недостающие методы
    List<UserEntity> findByNameContainingIgnoreCase(String name);
    List<UserEntity> findByEmailContainingIgnoreCase(String email);
    
    @Query("SELECT u FROM UserEntity u WHERE u.role.name = :roleName")
    List<UserEntity> findByRoleName(@Param("roleName") String roleName);
    
    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.enabled = true")
    Long countEnabledUsers();
}