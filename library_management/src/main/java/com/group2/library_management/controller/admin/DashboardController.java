package com.group2.library_management.controller.admin;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")
public class DashboardController {
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        return "admin/dashboard";
    }

    @GetMapping("/bookinstances")
    public String showBookInstance(Model model) {
        // Logic to fetch book instances can be added here
        return "admin/bookinstances/list";
    }
    
}
