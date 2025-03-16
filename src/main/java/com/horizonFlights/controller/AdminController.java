package com.horizonFlights.controller;

import com.horizonFlights.model.Admin;
import com.horizonFlights.service.FlightService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @Autowired
    FlightService flightService;

    @GetMapping("/admin-dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("adminLogin");
        if(admin == null) {
            return "redirect:/admin-login";
        }

        model.addAttribute("username", admin.getUsername());
        model.addAttribute("flights", flightService.getAllFlights());
        return "admin-dashboard";
    }
}
