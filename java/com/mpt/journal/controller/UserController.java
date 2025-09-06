package com.mpt.journal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mpt.journal.entity.UserEntity;
import com.mpt.journal.service.PassportService;
import com.mpt.journal.service.RoleService;
import com.mpt.journal.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PassportService passportService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String getAll(Model model) {
        logger.info("Получение списка всех пользователей.");
        model.addAttribute("users", userService.getAll());
        model.addAttribute("passports", passportService.getAll());
        model.addAttribute("roles", roleService.getAll());
        return "users";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute @Valid UserEntity user,
                         BindingResult result,
                         @RequestParam Long passportId,
                         @RequestParam Long roleId,
                         Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("users", userService.getAll());
            model.addAttribute("passports", passportService.getAll());
            model.addAttribute("roles", roleService.getAll());
            return "users";
        }

        var passport = passportService.getById(passportId);
        var role = roleService.getById(roleId);

        if (passport == null) {
            logger.error("Паспорт с ID {} не найден!", passportId);
            model.addAttribute("error", "Паспорт не найден");
            model.addAttribute("users", userService.getAll());
            model.addAttribute("passports", passportService.getAll());
            model.addAttribute("roles", roleService.getAll());
            return "users";
        }

        if (role == null) {
            logger.error("Роль с ID {} не найдена!", roleId);
            model.addAttribute("error", "Роль не найдена");
            model.addAttribute("users", userService.getAll());
            model.addAttribute("passports", passportService.getAll());
            model.addAttribute("roles", roleService.getAll());
            return "users";
        }

        user.setPassport(passport);
        user.setRole(role);
        userService.save(user);
        
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        logger.info("Удаление пользователя ID={}", id);
        userService.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchType, @RequestParam String searchValue, Model model) {
        logger.info("Поиск пользователей по типу={} и значению={}", searchType, searchValue);
        model.addAttribute("users", userService.search(searchType, searchValue));
        model.addAttribute("passports", passportService.getAll());
        model.addAttribute("roles", roleService.getAll());
        return "users";
    }
}