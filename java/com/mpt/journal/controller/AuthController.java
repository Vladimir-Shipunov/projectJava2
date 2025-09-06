package com.mpt.journal.controller;

import com.mpt.journal.entity.UserEntity;
import com.mpt.journal.service.RoleService;
import com.mpt.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                          @RequestParam(value = "logout", required = false) String logout,
                          Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
        }
        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String confirmPassword,
                             @RequestParam String name,
                             Model model) {
        
        // Валидация
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Пароли не совпадают");
            return "register";
        }

        if (userService.getUserByUsername(username) != null) {
            model.addAttribute("error", "Имя пользователя уже занято");
            return "register";
        }

        if (userService.getUserByEmail(email) != null) {
            model.addAttribute("error", "Email уже используется");
            return "register";
        }

        // Создание пользователя
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setEnabled(true);

        // Назначаем роль USER по умолчанию
        var userRole = roleService.findByName("USER");
        if (userRole == null) {
            model.addAttribute("error", "Системная ошибка: роль USER не найдена");
            return "register";
        }
        user.setRole(userRole);

        userService.save(user);

        return "redirect:/login?registered";
    }
}