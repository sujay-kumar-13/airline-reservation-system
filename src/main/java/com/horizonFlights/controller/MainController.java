package com.horizonFlights.controller;

import com.horizonFlights.model.Admin;
import com.horizonFlights.model.User;
import com.horizonFlights.service.AdminService;
import com.horizonFlights.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = new User(username, email, password);
        userService.registerUser(user);

        session.setAttribute("loggedInUser", user);
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = userService.authenticate(email, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/dashboard";
        } else {
            return "login";
        }
    }

    @GetMapping("/admin-login")
    public String adminLoginForm() {
        return "admin-login";
    }

    @PostMapping("/admin-login")
    public String adminLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Admin admin = adminService.authenticate(email, password);
        if(admin != null) {
            session.setAttribute("adminLogin", admin);
            return "redirect:/admin-dashboard";
        }
        else {
            return "admin-login";
        }
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        session.invalidate(); // Clear all session data
        return new RedirectView("/"); // Redirect to home page
    }
}
