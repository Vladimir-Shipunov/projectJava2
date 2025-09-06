package com.mpt.journal.controller;

import com.mpt.journal.entity.ProductEntity;
import com.mpt.journal.service.ProductService;
import com.mpt.journal.service.ProductCategoryService;
import com.mpt.journal.service.ManufacturerService;
import com.mpt.journal.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductsController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public String manageProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("manufacturers", manufacturerService.getAll());
        return "admin-products";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name,
                           @RequestParam String description,
                           @RequestParam Double price,
                           @RequestParam Long categoryId,
                           @RequestParam Long manufacturerId,
                           @RequestParam(required = false) MultipartFile image) throws IOException {
        
        imageService.validateImage(image);
        
        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(categoryService.getById(categoryId));
        product.setManufacturer(manufacturerService.getById(manufacturerId));

        if (image != null && !image.isEmpty()) {
            product.setImage(image.getBytes());
            product.setImageContentType(image.getContentType());
        }

        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        ProductEntity product = productService.getById(id);
        if (product == null) {
            return "redirect:/admin/products";
        }

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("manufacturers", manufacturerService.getAll());
        return "admin-edit-product";
    }

    @PostMapping("/update")
    public String updateProduct(@RequestParam Long id,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam Double price,
                              @RequestParam Long categoryId,
                              @RequestParam Long manufacturerId,
                              @RequestParam(required = false) MultipartFile image,
                              @RequestParam(required = false) String removeImage) throws IOException {
        
        imageService.validateImage(image);
        
        ProductEntity product = productService.getById(id);
        if (product != null) {
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setCategory(categoryService.getById(categoryId));
            product.setManufacturer(manufacturerService.getById(manufacturerId));

            if ("true".equals(removeImage)) {
                product.setImage(null);
                product.setImageContentType(null);
            } else if (image != null && !image.isEmpty()) {
                product.setImage(image.getBytes());
                product.setImageContentType(image.getContentType());
            }

            productService.save(product);
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
}