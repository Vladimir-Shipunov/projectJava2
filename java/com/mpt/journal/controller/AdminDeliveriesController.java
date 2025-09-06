package com.mpt.journal.controller;

import com.mpt.journal.entity.DeliveryEntity;
import com.mpt.journal.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/deliveries")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDeliveriesController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public String manageDeliveries(Model model) {
        model.addAttribute("deliveries", deliveryService.getAll());
        return "admin-deliveries";
    }

    @PostMapping("/add")
    public String addDelivery(@RequestParam String type,
                            @RequestParam Double cost,
                            @RequestParam(required = false) String description,
                            @RequestParam(required = false) String deliveryTime) {
        DeliveryEntity delivery = new DeliveryEntity();
        delivery.setType(type);
        delivery.setCost(cost);
        delivery.setDescription(description);
        delivery.setDeliveryTime(deliveryTime);
        deliveryService.save(delivery);
        return "redirect:/admin/deliveries";
    }

    @GetMapping("/edit/{id}")
    public String editDeliveryForm(@PathVariable Long id, Model model) {
        DeliveryEntity delivery = deliveryService.getById(id);
        if (delivery == null) {
            return "redirect:/admin/deliveries";
        }
        model.addAttribute("delivery", delivery);
        return "admin-edit-delivery";
    }

    @PostMapping("/update")
    public String updateDelivery(@RequestParam Long id,
                               @RequestParam String type,
                               @RequestParam Double cost,
                               @RequestParam(required = false) String description,
                               @RequestParam(required = false) String deliveryTime) {
        DeliveryEntity delivery = deliveryService.getById(id);
        if (delivery != null) {
            delivery.setType(type);
            delivery.setCost(cost);
            delivery.setDescription(description);
            delivery.setDeliveryTime(deliveryTime);
            deliveryService.save(delivery);
        }
        return "redirect:/admin/deliveries";
    }

    @PostMapping("/delete")
    public String deleteDelivery(@RequestParam Long id) {
        deliveryService.delete(id);
        return "redirect:/admin/deliveries";
    }
}