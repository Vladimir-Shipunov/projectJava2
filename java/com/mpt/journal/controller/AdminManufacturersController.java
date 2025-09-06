package com.mpt.journal.controller;

import com.mpt.journal.entity.ManufacturerEntity;
import com.mpt.journal.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/manufacturers")
@PreAuthorize("hasRole('ADMIN')")
public class AdminManufacturersController {

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping
    public String manageManufacturers(Model model) {
        model.addAttribute("manufacturers", manufacturerService.getAll());
        return "admin-manufacturers";
    }

    @PostMapping("/add")
    public String addManufacturer(@RequestParam String name,
                                @RequestParam String country,
                                @RequestParam(required = false) String address) {
        ManufacturerEntity manufacturer = new ManufacturerEntity();
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        manufacturer.setAddress(address);
        manufacturerService.save(manufacturer);
        return "redirect:/admin/manufacturers";
    }

    @GetMapping("/edit/{id}")
    public String editManufacturerForm(@PathVariable Long id, Model model) {
        ManufacturerEntity manufacturer = manufacturerService.getById(id);
        if (manufacturer == null) {
            return "redirect:/admin/manufacturers";
        }
        model.addAttribute("manufacturer", manufacturer);
        return "admin-edit-manufacturer";
    }

    @PostMapping("/update")
    public String updateManufacturer(@RequestParam Long id,
                                   @RequestParam String name,
                                   @RequestParam String country,
                                   @RequestParam(required = false) String address) {
        ManufacturerEntity manufacturer = manufacturerService.getById(id);
        if (manufacturer != null) {
            manufacturer.setName(name);
            manufacturer.setCountry(country);
            manufacturer.setAddress(address);
            manufacturerService.save(manufacturer);
        }
        return "redirect:/admin/manufacturers";
    }

    @PostMapping("/delete")
    public String deleteManufacturer(@RequestParam Long id) {
        manufacturerService.delete(id);
        return "redirect:/admin/manufacturers";
    }
}