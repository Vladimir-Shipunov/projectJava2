package com.mpt.journal.controller;

import com.mpt.journal.entity.ProductCategoryEntity;
import com.mpt.journal.service.ProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", service.getAll());
        model.addAttribute("currentPage", 0);
        return "productCategoryList";
    }

    @PostMapping("/add")
public String add(@ModelAttribute @Valid ProductCategoryEntity category, BindingResult result, Model model) {
    if (result.hasErrors()) {
        model.addAttribute("categories", service.getAll());
        return "productCategoryList";
    }
    service.save(category); 
    return "redirect:/categories";
}

@PostMapping("/update")
public String update(@ModelAttribute @Valid ProductCategoryEntity category) {
    service.save(category); 
    return "redirect:/categories";
}

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        service.delete(id);
        return "redirect:/categories";
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchType, @RequestParam String searchValue, Model model) {
        model.addAttribute("categories", service.search(searchType, searchValue));
        model.addAttribute("currentPage", 0);
        return "productCategoryList";
    }
}
