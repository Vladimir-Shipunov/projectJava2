package com.mpt.journal.controller;

import com.mpt.journal.entity.ProductCategoryEntity;
import com.mpt.journal.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoriesController {

    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping
    public String manageCategories(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "admin-categories";
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam String name,
                            @RequestParam(required = false) String description) {
        ProductCategoryEntity category = new ProductCategoryEntity();
        category.setName(name);
        category.setDescription(description);
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        ProductCategoryEntity category = categoryService.getById(id);
        if (category == null) {
            return "redirect:/admin/categories";
        }
        model.addAttribute("category", category);
        return "admin-edit-category";
    }

    @PostMapping("/update")
    public String updateCategory(@RequestParam Long id,
                               @RequestParam String name,
                               @RequestParam(required = false) String description) {
        ProductCategoryEntity category = categoryService.getById(id);
        if (category != null) {
            category.setName(name);
            category.setDescription(description);
            categoryService.save(category);
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/delete")
    public String deleteCategory(@RequestParam Long id) {
        categoryService.delete(id);
        return "redirect:/admin/categories";
    }
}