package com.horizonFlights.controller;

import com.horizonFlights.model.Flight;
import com.horizonFlights.model.User;
import com.horizonFlights.service.TicketsService;
import com.horizonFlights.service.FlightService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/search-flights")
class SearchFlightsController {

    @Autowired
    private TicketsService ticketsService;

    @Autowired
    private FlightService flightService;

    @PostMapping
    public String searchFlights(@RequestParam String origin,
                                @RequestParam String destination,
                                @RequestParam String departureDate,
                                Model model,
                                HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate departure = LocalDate.parse(departureDate, formatter);

        List<Flight> flights = flightService.searchFlights(origin, destination, departure);
        model.addAttribute("flights", flights);
        model.addAttribute("userEmail", user.getEmail());
        return "search-flights"; // HTML page to display available flights
    }

    @GetMapping
    public String searchFlights() {
        System.err.println("Searching");
        return "redirect:/login";
    }
}
