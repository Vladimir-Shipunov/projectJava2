package com.mpt.journal.controller;

import com.mpt.journal.entity.ManufacturerEntity;
import com.mpt.journal.service.ManufacturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    @Autowired
    private ManufacturerService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("manufacturers", service.getAll());
        model.addAttribute("currentPage", 0);
        return "manufacturerList";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid ManufacturerEntity manufacturer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("manufacturers", service.getAll());
            return "manufacturerList";
        }
        service.save(manufacturer);
        return "redirect:/manufacturers";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute @Valid ManufacturerEntity manufacturer) {
        service.save(manufacturer); 
        return "redirect:/manufacturers";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        service.delete(id);
        return "redirect:/manufacturers";
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchType, @RequestParam String searchValue, Model model) {
        model.addAttribute("manufacturers", service.search(searchType, searchValue));
        model.addAttribute("currentPage", 0);
        return "manufacturerList";
    }
}
