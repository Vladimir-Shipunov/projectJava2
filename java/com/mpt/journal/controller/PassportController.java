package com.mpt.journal.controller;

import com.mpt.journal.entity.PassportEntity;
import com.mpt.journal.service.PassportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passports")
public class PassportController {

    @Autowired
    private PassportService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("passports", service.getAll());
        model.addAttribute("currentPage", 0);
        return "passportList";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid PassportEntity passport, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("passports", service.getAll());
            return "passportList";
        }
        service.save(passport);  
        return "redirect:/passports";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute @Valid PassportEntity passport) {
        service.save(passport); 
        return "redirect:/passports";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        service.delete(id);
        return "redirect:/passports";
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchType, @RequestParam String searchValue, Model model) {
        model.addAttribute("passports", service.search(searchType, searchValue));
        model.addAttribute("currentPage", 0);
        return "passportList";
    }
}
