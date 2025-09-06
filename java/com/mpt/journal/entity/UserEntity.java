package com.mpt.journal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Имя пользователя обязательно")
    @Column(unique = true, nullable = false)
    private String username;
    
    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный формат email")
    @Column(unique = true, nullable = false)
    private String email;
    
    @NotBlank(message = "Пароль обязателен")
    @Column(nullable = false)
    private String password;
    
    @NotBlank(message = "Имя обязательно")
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "passport_id")
    private PassportEntity passport;
    
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
    
    // ПОЛЯ ДЛЯ SPRING SECURITY (ОБЯЗАТЕЛЬНЫЕ)
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    
    // Конструкторы
    public UserEntity() {}
    
    public UserEntity(String username, String email, String password, String name, RoleEntity role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
    
    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public PassportEntity getPassport() { return passport; }
    public void setPassport(PassportEntity passport) { this.passport = passport; }
    
    public RoleEntity getRole() { return role; }
    public void setRole(RoleEntity role) { this.role = role; }
    
    public void setAccountNonExpired(boolean accountNonExpired) { this.accountNonExpired = accountNonExpired; }
    public void setAccountNonLocked(boolean accountNonLocked) { this.accountNonLocked = accountNonLocked; }
    public void setCredentialsNonExpired(boolean credentialsNonExpired) { this.credentialsNonExpired = credentialsNonExpired; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    
    // МЕТОДЫ UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}