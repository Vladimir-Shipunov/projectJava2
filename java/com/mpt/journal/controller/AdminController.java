package com.mpt.journal.controller;

import com.mpt.journal.entity.*;
import com.mpt.journal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public String adminDashboard(Model model) {
        // Статистика для дашборда
        model.addAttribute("ordersCount", orderService.getAll().size());
        model.addAttribute("productsCount", productService.getAll().size());
        model.addAttribute("usersCount", userService.getAll().size());
        
        // Расчет общей выручки
        double totalRevenue = orderService.getAll().stream()
                .mapToDouble(OrderEntity::getTotal)
                .sum();
        model.addAttribute("totalRevenue", totalRevenue);
        
        // Последние 5 заказов
        List<OrderEntity> recentOrders = orderService.getAll().stream()
                .limit(5)
                .toList();
        model.addAttribute("recentOrders", recentOrders);

        return "admin-dashboard";
    }

    // УДАЛЕНО: Дублирующиеся методы управления продуктами, категориями, производителями и доставкой
    // Эти функции теперь в отдельных контроллерах: AdminProductsController, AdminCategoriesController и т.д.

    @GetMapping("/orders")
    public String manageOrders(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "admin-orders";
    }

    @PostMapping("/orders/update-status")
    public String updateOrderStatus(@RequestParam Long id, @RequestParam String status) {
        OrderEntity order = orderService.getById(id);
        if (order != null) {
            order.setStatus(status);
            orderService.save(order);
        }
        return "redirect:/admin/orders";
    }
}