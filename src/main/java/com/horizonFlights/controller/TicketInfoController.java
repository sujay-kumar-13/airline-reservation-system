package com.horizonFlights.controller;

import com.horizonFlights.model.Tickets;
import com.horizonFlights.model.User;
import com.horizonFlights.repository.TicketsRepository;
import com.horizonFlights.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TicketInfoController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketsRepository ticketsRepository;

    @PostMapping("/cancel-ticket")
    public String cancelTicket(@RequestParam("ticketId") String id, HttpSession session, Model model) {
        // Find the ticket in the database
        Query query = new Query(Criteria.where("tickets._id").is(id));
        User user = mongoTemplate.findOne(query, User.class);

        if (user == null || user.getTickets() == null) {
            return "redirect:/dashboard";
        }

        Tickets selectedTicket = user.getTickets().stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (selectedTicket == null) {
            return "redirect:/dashboard";
        }

        selectedTicket.setStatus("Cancelled");

        // **Update the ticket status in MongoDB**
        Update update = new Update().set("tickets.$.status", "Cancelled");
        mongoTemplate.updateFirst(query, update, User.class);

        model.addAttribute("ticket", selectedTicket);

        return "ticket-info";
    }

    @GetMapping("/ticket-info")
    public String openTicketInfo(@RequestParam("ticketId") String id, HttpSession session, Model model) {
        // Get logged-in user from session
        User user = (User) session.getAttribute("loggedInUser");

        // Check if user is logged in
        if (user == null) {
            return "redirect:/login"; // Redirect if no user is logged in
        }

        // Find the ticket with the given ID from the user's tickets
        Tickets selectedTicket = user.getTickets().stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .orElse(null);

        // Check if the ticket exists
        if (selectedTicket == null) {
            return "redirect:/dashboard"; // Redirect if ticket not found
        }

        // Add ticket details to the model
        model.addAttribute("ticket", selectedTicket);

        return "ticket-info"; // Load ticket-info.html
    }

//    @PostMapping("/cancel-ticket")
//    public String cancelTicket(@RequestParam("ticketId") String id, HttpSession session, Model model) {
//        // Get logged-in user from session
//        User user = (User) session.getAttribute("loggedInUser");
//
//        // Check if user is logged in
//        if (user == null) {
//            return "redirect:/login"; // Redirect if no user is logged in
//        }
//
//        // Find the ticket with the given ID from the user's tickets
//        Tickets selectedTicket = user.getTickets().stream()
//                .filter(ticket -> ticket.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//
//        // Check if the ticket exists
//        if (selectedTicket == null) {
//            return "redirect:/dashboard"; // Redirect if ticket not found
//        }
//
//        selectedTicket.setStatus("Cancelled");
//        // Add ticket details to the model
//        model.addAttribute("ticket", selectedTicket);
//
//        return "ticket-info"; // Load ticket-info.html
//    }

//    @GetMapping("/ticket-info")
    public String ticketInfo(@PathVariable String ticketId, HttpSession session, Model model) {
        // Get logged-in user from session
        User user = (User) session.getAttribute("loggedInUser");

        // Check if user is logged in
        if (user == null) {
            return "redirect:/login"; // Redirect if no user is logged in
        }

        // Find the ticket with the given ID from the user's tickets
        Tickets selectedTicket = user.getTickets().stream()
                .filter(ticket -> ticket.getId().equals(ticketId))
                .findFirst()
                .orElse(null);

        // Check if the ticket exists
        if (selectedTicket == null) {
            return "redirect:/dashboard"; // Redirect if ticket not found
        }

        // Add ticket details to the model
        model.addAttribute("ticket", selectedTicket);

        return "ticket-info"; // Load ticket-info.html
    }

}
