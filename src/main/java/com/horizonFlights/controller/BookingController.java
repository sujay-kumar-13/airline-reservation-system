package com.horizonFlights.controller;

import com.horizonFlights.model.Flight;
import com.horizonFlights.model.Tickets;
import com.horizonFlights.model.User;
import com.horizonFlights.repository.FlightRepository;
import com.horizonFlights.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book-tickets")
public class BookingController {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    UserRepository userRepository;

//    @GetMapping
//    public String bookTicket() {
//        return "book-tickets";
//    }

//    @PostMapping
//    public String bookTicket(@RequestParam String email, @RequestBody Tickets ticket) {
//
//        User user = userRepository.findByEmail(email);
//
//        if (user != null) {
////            User user = optionalUser.get();
//            user.getTickets().add(ticket);  // Add new ticket to user's list
//            userRepository.save(user);
//            return "redirect:/dashboard";// Save updated user data
//        } else {
//            throw new RuntimeException("User not found!");
//        }
//    }

    @PostMapping
    public String bookTickets(@RequestParam("flightId") String flightId,
                              @RequestParam("selectedClass") String selectedClass,
                              HttpSession session, Model model) {
        // ✅ Check if user is logged in
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        // ✅ Fetch flight details from MongoDB
        Flight flight = flightRepository.findById(flightId).orElse(null);
        if (flight == null) {
            model.addAttribute("errorMessage", "Flight not found!");
            return "error"; // Redirect to an error page
        }

        // ✅ Add flight details to the model
        model.addAttribute("flight", flight);
        model.addAttribute("selectedClass", selectedClass);

        // ✅ Set unit price based on class
        double unitPrice = switch (selectedClass) {
            case "economy" -> flight.getEconomyClassPrice();
            case "business" -> flight.getBusinessClassPrice();
            case "first" -> flight.getFirstClassPrice();
            default -> 0.0;
        };

        model.addAttribute("unitPrice", unitPrice);
        model.addAttribute("totalAmount", 0.0);
        model.addAttribute("userEmail", user.getEmail());

        return "book-tickets"; // Return the booking page
    }
}
