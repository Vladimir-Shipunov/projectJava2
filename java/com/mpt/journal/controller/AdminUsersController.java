package com.mpt.journal.controller;

import com.mpt.journal.entity.UserEntity;
import com.mpt.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUsersController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "admin-users";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam String searchType,
                            @RequestParam String searchValue,
                            Model model) {
        model.addAttribute("users", userService.search(searchType, searchValue));
        return "admin-users";
    }

    @PostMapping("/toggle-status")
    public String toggleUserStatus(@RequestParam Long id) {
        UserEntity user = userService.getById(id);
        if (user != null) {
            user.setEnabled(!user.isEnabled());
            userService.save(user);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        UserEntity user = userService.getById(id);
        if (user == null) {
            return "redirect:/admin/users";
        }
        model.addAttribute("user", user);
        // Добавить списки ролей и паспортов если нужно
        return "admin-edit-user";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam Long id,
                           @RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String username) {
        UserEntity user = userService.getById(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setUsername(username);
            userService.save(user);
        }
        return "redirect:/admin/users";
    }
}