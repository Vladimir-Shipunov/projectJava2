package com.mpt.journal.controller;

import com.mpt.journal.entity.RoleEntity;
import com.mpt.journal.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("roles", service.getAll());
        model.addAttribute("currentPage", 0);
        return "roleList";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid RoleEntity role, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", service.getAll());
            return "roleList";
        }
        service.save(role); 
        return "redirect:/roles";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute @Valid RoleEntity role) {
        service.save(role); 
        return "redirect:/roles";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        service.delete(id);
        return "redirect:/roles";
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchType, @RequestParam String searchValue, Model model) {
        model.addAttribute("roles", service.search(searchType, searchValue));
        model.addAttribute("currentPage", 0);
        return "roleList";
    }
}
