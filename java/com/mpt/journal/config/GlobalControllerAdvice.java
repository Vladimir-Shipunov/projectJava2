package com.mpt.journal.config;

import com.mpt.journal.service.AuthService;
import com.mpt.journal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private AuthService authService;

    @Autowired
    private CartService cartService;

    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated() {
        return authService.isAuthenticated();
    }

    @ModelAttribute("currentUser")
    public Object getCurrentUser() {
        return authService.getCurrentUser();
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        return authService.hasRole("ADMIN");
    }

    @ModelAttribute("cartItemCount")
    public int getCartItemCount() {
        if (authService.isAuthenticated()) {
            return cartService.getCartItemCount(authService.getCurrentUser());
        }
        return 0;
    }
}