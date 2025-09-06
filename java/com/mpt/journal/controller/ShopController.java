package com.mpt.journal.controller;

import com.mpt.journal.entity.ProductEntity;
import com.mpt.journal.entity.ProductCategoryEntity;
import com.mpt.journal.entity.ManufacturerEntity;
import com.mpt.journal.entity.UserEntity;
import com.mpt.journal.repository.ProductRepository;
import com.mpt.journal.repository.ProductCategoryRepository;
import com.mpt.journal.repository.ManufacturerRepository;
import com.mpt.journal.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ShopController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private AuthService authService;

    @GetMapping("/shops")
    public String shopPage(Model model,
                           @RequestParam(required = false) Long categoryId,
                           @RequestParam(required = false) Long manufacturerId,
                           Principal principal) {

        // Получаем пользователя
        UserEntity user = authService.getUserByPrincipal(principal);

        // Получаем все категории и производителей
        List<ProductCategoryEntity> categories = productCategoryRepository.findAll();
        List<ManufacturerEntity> manufacturers = manufacturerRepository.findAll();

        // Получаем товары с учётом фильтров
        List<ProductEntity> products;
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else if (manufacturerId != null) {
            products = productRepository.findByManufacturerId(manufacturerId);
        } else {
            products = productRepository.findAll();
        }

        // Передаём данные в модель
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("manufacturers", manufacturers);

        model.addAttribute("isAuthenticated", user != null);
        model.addAttribute("isAdmin", authService.isAdmin(user));
        model.addAttribute("cartItemCount", authService.getCartItemCount(user));

        return "shop";
    }
}
