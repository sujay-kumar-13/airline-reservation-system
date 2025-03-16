package com.horizonFlights.controller;

import com.horizonFlights.model.Admin;
import com.horizonFlights.model.Flight;
import com.horizonFlights.repository.FlightRepository;
import com.horizonFlights.service.FlightService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class FlightController {
//    private final FlightRepository flightRepository;
//
//    public FlightController(FlightRepository flightRepository) {
//        this.flightRepository = flightRepository;
//    }

    @Autowired
    FlightService flightService;

    @PostMapping("/add-flight")
    public String addFlight(@RequestParam String flightNumber,
                            @RequestParam String origin,
                            @RequestParam String destination,
                            @RequestParam String departureDate,
                            @RequestParam String departureTime,
                            @RequestParam String arrivalDate,
                            @RequestParam String arrivalTime,
                            @RequestParam double economyClassPrice,
                            @RequestParam double businessClassPrice,
                            @RequestParam double firstClassPrice,
                            @RequestParam int economySeats,
                            @RequestParam int businessSeats,
                            @RequestParam int firstClassSeats,
                            HttpSession session) {

        // Parse date and time separately
        LocalDate depDate = LocalDate.parse(departureDate);
        LocalTime depTime = LocalTime.parse(departureTime, DateTimeFormatter.ofPattern("HH:mm"));

        LocalDate arrDate = LocalDate.parse(arrivalDate);
        LocalTime arrTime = LocalTime.parse(arrivalTime, DateTimeFormatter.ofPattern("HH:mm"));

        List<Integer> economyAvailable = IntStream.rangeClosed(1, economySeats).boxed().collect(Collectors.toList());
        List<Integer> businessAvailable = IntStream.rangeClosed(1, businessSeats).boxed().collect(Collectors.toList());
        List<Integer> firstAvailable = IntStream.rangeClosed(1, firstClassSeats).boxed().collect(Collectors.toList());


        Flight flight = new Flight(flightNumber, origin, destination, depDate, depTime, arrDate, arrTime, economyClassPrice, businessClassPrice, firstClassPrice, economyAvailable, businessAvailable, firstAvailable);
        flightService.addFlight(flight);

        return "redirect:/admin-dashboard";  // Redirect back to admin panel
    }

//    @PostMapping("/add-flight")
//    public String addFlight(@RequestParam String flightNumber,
//                            @RequestParam String origin,
//                            @RequestParam String destination,
//                            @RequestParam String departureTime,
//                            @RequestParam String arrivalTime,
//                            @RequestParam double economyClassPrice,
//                            @RequestParam(required = false) double businessClassPrice,
//                            @RequestParam(required = false) double firstClassPrice,
//                            HttpSession session) {
//
//        Admin admin = (Admin) session.getAttribute("adminLogin");
//        if (admin == null) {
//            return "redirect:/admin-login";
//        }
//
//        try {
//            // Define the date format
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//
//            // Parse the dates correctly
//            LocalDateTime departure = LocalDateTime.parse(departureTime, formatter);
//            LocalDateTime arrival = LocalDateTime.parse(arrivalTime, formatter);
//
//            Flight flight = new Flight(flightNumber, origin, destination, departure, arrival, economyClassPrice, businessClassPrice, firstClassPrice);
//            flightService.addFlight(flight);
//
//            return "redirect:/admin-dashboard";
//        } catch (Exception e) {
//            e.printStackTrace(); // Print error in console
//            return "error-page"; // Redirect to a custom error page
//        }
//    }
}
