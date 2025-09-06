package com.mpt.journal.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mpt.journal.entity.UserEntity;

@Service
public class AuthService {

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && 
               !authentication.getName().equals("anonymousUser");
    }

    public UserEntity getCurrentUser() {
        if (!isAuthenticated()) {
            return null;
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserEntity) {
            return (UserEntity) authentication.getPrincipal();
        }
        return null;
    }

    public boolean hasRole(String roleName) {
        UserEntity user = getCurrentUser();
        return user != null && user.getRole() != null && 
               user.getRole().getName().equalsIgnoreCase(roleName);
    }

    public String getCurrentUsername() {
        UserEntity user = getCurrentUser();
        return user != null ? user.getUsername() : null;
    }
}