package com.horizonFlights.controller;

import com.horizonFlights.model.Flight;
import com.horizonFlights.model.Tickets;
import com.horizonFlights.model.User;
import com.horizonFlights.repository.FlightRepository;
import com.horizonFlights.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/save-tickets")
public class TicketsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlightRepository flightRepository;

//    @PostMapping("/book-ticket")
//    public ResponseEntity<Map<String, String>> bookTicket(@RequestParam String email, @RequestBody Tickets ticket) {
//        User user = userRepository.findByEmail(email);
//
//        if (user != null) {
//            if (user.getTickets() == null) {  // Prevent null pointer exception
//                user.setTickets(new ArrayList<>());
//            }
//            String ticketId = UUID.randomUUID().toString();  // Generate ticket ID
//            ticket.setId(ticketId);
//            user.getTickets().add(ticket);
//            userRepository.save(user);
//
//            // Return response with ticketId and userEmail
//            Map<String, String> response = new HashMap<>();
//            response.put("ticketId", ticketId);
//            response.put("userEmail", email);
//            return ResponseEntity.ok(response);
//        } else {
//            throw new RuntimeException("User not found!");
//        }
//    }


    @PostMapping
    public ResponseEntity<Map<String, String>> bookTicket(@RequestParam String email, @RequestBody Tickets ticket, HttpSession session) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            if (user.getTickets() == null) {  // Prevent null pointer exception
                user.setTickets(new ArrayList<>());
            }

            ticket.setId(UUID.randomUUID().toString());  // Ensure unique ID is assigned

            Flight flight = flightRepository.findById(ticket.getFlightId())
                    .orElseThrow(() -> new RuntimeException("Flight not found!"));

            int passengers = ticket.getPassengers().size();
            List<Integer> assignedSeats;

            // Check seat availability based on ticket class
            switch (ticket.getSelectedClass().toLowerCase()) {
                case "economy":
                    if (passengers > flight.getEconomySeats().size()) {
                        throw new RuntimeException("Not enough economy seats available!");
                    }
                    assignedSeats = assignSeats(passengers, flight.getEconomySeats()); // Assign seats
                    flight.getEconomySeats().removeAll(assignedSeats); // Remove assigned seats from available list
                    break;

                case "business":
                    if (passengers > flight.getBusinessSeats().size()) {
                        throw new RuntimeException("Not enough business seats available!");
                    }
                    assignedSeats = assignSeats(passengers, flight.getBusinessSeats());
                    flight.getBusinessSeats().removeAll(assignedSeats);
                    break;

                case "first":
                    if (passengers > flight.getFirstClassSeats().size()) {
                        throw new RuntimeException("Not enough first-class seats available!");
                    }
                    assignedSeats = assignSeats(passengers, flight.getFirstClassSeats());
                    flight.getFirstClassSeats().removeAll(assignedSeats);
                    break;

                default:
                    throw new RuntimeException("Invalid seat class!");
            }


            // Assign seats to passengers inside the ticket
            for (int i = 0; i < passengers; i++) {
                ticket.getPassengers().get(i).setSeat(assignedSeats.get(i));
            }
            ticket.setStatus("Booked");
            user.getTickets().add(ticket);  // Add new ticket to user's list

            flightRepository.save(flight);  // Save updated flight seat data
            userRepository.save(user);  // Save updated user data
            session.setAttribute("loggedInUser", user);

            return ResponseEntity.ok(Map.of("message", "Ticket booked successfully"));
//            return ResponseEntity.ok("Ticket Booked Successfully");
        } else {
            throw new RuntimeException("User not found!");
        }
    }

    @GetMapping
    public User getUserTickets(@PathVariable String email) {
        return userRepository.findByEmail(email);
    }

    private List<Integer> assignSeats(int passengers, List<Integer> availableSeats) {
        List<Integer> shuffledSeats = new ArrayList<>(availableSeats); // Copy to avoid modifying original list
        Collections.shuffle(shuffledSeats); // Shuffle the seat list
        return new ArrayList<>(shuffledSeats.subList(0, passengers)); // Pick first N random seats
    }


//    private ArrayList<Integer> assignSeats(int passengers, int availableSeats) {
//        ArrayList<Integer> assignedSeats = new ArrayList<>();
//        Random rand = new Random();
//
//        HashSet<Integer> usedSeats = new HashSet<>();
//        while (assignedSeats.size() < passengers) {
//            int seatNumber = rand.nextInt(availableSeats) + 1;  // Generate seat between 1 and availableSeats
//            if (!usedSeats.contains(seatNumber)) {
//                usedSeats.add(seatNumber);
//                assignedSeats.add(seatNumber);
//            }
//        }
//        return assignedSeats;
//    }

}
