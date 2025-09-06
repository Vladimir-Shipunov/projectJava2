package com.mpt.journal.controller;

import com.mpt.journal.entity.PayEntity;
import com.mpt.journal.service.PayService;
import com.mpt.journal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payments")
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("payments", payService.getAll());
        model.addAttribute("orders", orderService.getAll());
        return "payList";
    }

    @PostMapping("/add")
    public String addPayment(
            @RequestParam String paymentType,
            @RequestParam Double amount,
            @RequestParam String status,
            @RequestParam String paymentDate,
            @RequestParam Long orderId
    ) {
        PayEntity payment = new PayEntity();
        payment.setPaymentType(paymentType);
        payment.setAmount(amount);
        payment.setStatus(status);
        payment.setPaymentDate(paymentDate);
        payment.setOrder(orderService.getById(orderId));
        payService.save(payment);
        return "redirect:/payments";
    }

    @PostMapping("/update")
    public String updatePayment(
            @RequestParam Long id,
            @RequestParam String paymentType,
            @RequestParam Double amount,
            @RequestParam String status,
            @RequestParam String paymentDate,
            @RequestParam Long orderId
    ) {
        PayEntity payment = payService.getById(id);
        if (payment != null) {
            payment.setPaymentType(paymentType);
            payment.setAmount(amount);
            payment.setStatus(status);
            payment.setPaymentDate(paymentDate);
            payment.setOrder(orderService.getById(orderId));
            payService.save(payment);
        }
        return "redirect:/payments";
    }

    @PostMapping("/delete")
    public String deletePayment(@RequestParam Long id) {
        payService.delete(id);
        return "redirect:/payments";
    }

    @GetMapping("/search")
    public String searchPayments(
            @RequestParam String searchType,
            @RequestParam String searchValue,
            Model model
    ) {
        model.addAttribute("payments", payService.search(searchType, searchValue));
        model.addAttribute("orders", orderService.getAll());
        return "payList";
    }
}
