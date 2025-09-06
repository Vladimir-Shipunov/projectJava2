package com.mpt.journal.controller;

import com.mpt.journal.entity.*;
import com.mpt.journal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/shop/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/checkout")
    public String checkoutPage(@AuthenticationPrincipal UserEntity user, Model model) {
        if (user == null) {
            return "redirect:/login";
        }

        List<CartItem> cartItems = cartService.getUserCart(user);
        if (cartItems.isEmpty()) {
            return "redirect:/shop/cart";
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", cartService.getCartTotal(user));
        model.addAttribute("user", user);
        model.addAttribute("deliveries", deliveryService.getAll());

        return "checkout";
    }

     @PostMapping("/create")
    public String createOrder(@AuthenticationPrincipal UserEntity user,
                            @RequestParam String customerName,
                            @RequestParam String phone,
                            @RequestParam String deliveryAddress,
                            @RequestParam Long deliveryId,
                            @RequestParam String paymentType) {
        
        if (user == null) {
            return "redirect:/login";
        }

        List<CartItem> cartItems = cartService.getUserCart(user);
        if (cartItems.isEmpty()) {
            return "redirect:/shop/cart";
        }

        // Создание заказа
        OrderEntity order = new OrderEntity();
        order.setCustomerName(customerName);
        order.setPhone(phone);
        order.setDeliveryAddress(deliveryAddress);
        order.setUser(user);
        
        DeliveryEntity delivery = deliveryService.getById(deliveryId);
        if (delivery == null) {
            return "redirect:/shop/checkout?error=delivery";
        }
        order.setDelivery(delivery);
        
        order.setPaymentType(paymentType);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        // Добавляем товары из корзины
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            order.addItem(orderItem);
        }

        // Расчет общей суммы
        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        order.setTotal(total + delivery.getCost());

        // Сохранение заказа
        orderService.save(order);

        // Очистка корзины
        cartService.clearCart(user);

        return "redirect:/shop/order/success/" + order.getId();
    }

    @GetMapping("/success/{id}")
    public String orderSuccess(@PathVariable Long id, Model model) {
        OrderEntity order = orderService.getById(id);
        if (order == null) {
            return "redirect:/shop";
        }

        model.addAttribute("orderId", order.getId());
        model.addAttribute("total", order.getTotal());
        model.addAttribute("deliveryType", order.getDelivery().getType());

        return "order-success";
    }

    @GetMapping("/{id}")
    public String orderDetails(@PathVariable Long id, 
                             @AuthenticationPrincipal UserEntity user, 
                             Model model) {
        OrderEntity order = orderService.getById(id);
        if (order == null) {
            return "redirect:/shop";
        }

        // Проверка прав доступа
        if (!user.getRole().getName().equals("ADMIN") && !order.getUser().getId().equals(user.getId())) {
            return "redirect:/shop";
        }

        model.addAttribute("order", order);
        return "order-details";
    }

    @GetMapping("/history")
    public String orderHistory(@AuthenticationPrincipal UserEntity user, Model model) {
        if (user == null) {
            return "redirect:/login";
        }

        // Здесь нужно реализовать получение заказов пользователя
        // Пока используем все заказы для примера
        List<OrderEntity> orders = orderService.getAll().stream()
                .filter(order -> order.getUser().getId().equals(user.getId()))
                .toList();

        model.addAttribute("orders", orders);
        return "order-history";
    }
}