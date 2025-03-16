package com.horizonFlights.controller;

import com.horizonFlights.model.Tickets;
import com.horizonFlights.model.User;
import com.horizonFlights.service.TicketsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    TicketsService ticketsService;

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        Query query = new Query(Criteria.where("email").is(user.getEmail()));
        User updatedUser = mongoTemplate.findOne(query, User.class);

        List<Tickets> upcomingTickets = ticketsService.getUpcomingFlights(updatedUser.getId()); // Replace with your service call
        List<Tickets> pastTickets = ticketsService.getPastFlights(updatedUser.getId()); // Replace with your service call

        model.addAttribute("upcomingTickets", upcomingTickets);
        model.addAttribute("pastTickets", pastTickets);
        model.addAttribute("username", updatedUser.getUsername());
        session.setAttribute("loggedInUser", updatedUser);
        return "dashboard";
    }
}
