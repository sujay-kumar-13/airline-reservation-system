package com.horizonFlights.service;

import com.horizonFlights.model.Tickets;
import com.horizonFlights.model.User;
import com.horizonFlights.repository.TicketsRepository;
import com.horizonFlights.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketsService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private TicketsRepository ticketsRepository;

    public List<Tickets> getUpcomingFlights(String userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null || user.getTickets() == null) {
            return List.of(); // Return empty list if user or tickets are null
        }

        LocalDateTime now = LocalDateTime.now();

        return user.getTickets().stream()
                .filter(ticket -> {
                    LocalDateTime departureDateTime = LocalDateTime.of(ticket.getDepartureDate(), ticket.getDepartureTime());
                    return departureDateTime.isAfter(now); // ✅ Check if departure time is in the future
                })
                .sorted((t1, t2) -> {
                    LocalDateTime dt1 = LocalDateTime.of(t1.getDepartureDate(), t1.getDepartureTime());
                    LocalDateTime dt2 = LocalDateTime.of(t2.getDepartureDate(), t2.getDepartureTime());
                    return dt1.compareTo(dt2); // Sort by earliest departure
                })
                .collect(Collectors.toList());
    }

    public List<Tickets> getPastFlights(String userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null || user.getTickets() == null) {
            return List.of(); // Return empty list if user or tickets are null
        }

        LocalDateTime now = LocalDateTime.now();

        return user.getTickets().stream()
                .filter(ticket -> {
                    LocalDateTime departureDateTime = LocalDateTime.of(ticket.getDepartureDate(), ticket.getDepartureTime());
                    return departureDateTime.isBefore(now); // ✅ Check if departure time is in the past
                })
                .sorted((t1, t2) -> {
                    LocalDateTime dt1 = LocalDateTime.of(t1.getDepartureDate(), t1.getDepartureTime());
                    LocalDateTime dt2 = LocalDateTime.of(t2.getDepartureDate(), t2.getDepartureTime());
                    return dt2.compareTo(dt1); // Sort by latest departure
                })
                .collect(Collectors.toList());
    }

//    public List<Tickets> getUpcomingFlights(String userId) {
//        return ticketsRepository.findByFlightIdAndDepartureTimeAfterOrderByDepartureTimeAsc(userId, LocalDateTime.now());
//    }
//
//    public List<Tickets> getPastFlights(String userId) {
//        return ticketsRepository.findByFlightIdAndDepartureTimeBeforeOrderByDepartureTimeDesc(userId, LocalDateTime.now());
//    }
}
