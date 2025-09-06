package com.mpt.journal.controller;

import com.mpt.journal.entity.DeliveryEntity;
import com.mpt.journal.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("deliveries", deliveryService.getAll());
        model.addAttribute("currentPage", 0);
        return "deliveryList";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute @Valid DeliveryEntity delivery, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("deliveries", deliveryService.getAll());
            return "deliveryList";
        }
        deliveryService.save(delivery);
        return "redirect:/deliveries";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute @Valid DeliveryEntity delivery) {
        deliveryService.save(delivery);
        return "redirect:/deliveries";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        deliveryService.delete(id);
        return "redirect:/deliveries";
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchType, @RequestParam String searchValue, Model model) {
        model.addAttribute("deliveries", deliveryService.search(searchType, searchValue));
        model.addAttribute("currentPage", 0);
        return "deliveryList";
    }
}
