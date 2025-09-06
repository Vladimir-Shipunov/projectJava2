package com.mpt.journal.controller;

import com.mpt.journal.entity.ProductEntity;
import com.mpt.journal.service.ProductService;
import com.mpt.journal.service.ProductCategoryService;
import com.mpt.journal.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("products", productService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("manufacturers", manufacturerService.getAll());
        return "productList";
    }

    @PostMapping("/add")
    public String addProduct(
        @RequestParam String name,
        @RequestParam String description,
        @RequestParam Double price,
        @RequestParam Long categoryId,
        @RequestParam Long manufacturerId
    ) {
        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(categoryService.getById(categoryId));
        product.setManufacturer(manufacturerService.getById(manufacturerId));
        productService.save(product);
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateProduct(
        @RequestParam Long id,
        @RequestParam String name,
        @RequestParam String description,
        @RequestParam Double price,
        @RequestParam Long categoryId,
        @RequestParam Long manufacturerId
    ) {
        ProductEntity product = productService.getById(id);
        if (product != null) {
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setCategory(categoryService.getById(categoryId));
            product.setManufacturer(manufacturerService.getById(manufacturerId));
            productService.save(product);
        }
        return "redirect:/products";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam Long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}