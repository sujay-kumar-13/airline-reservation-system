package com.horizonFlights.repository;

import com.horizonFlights.model.Tickets;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketsRepository extends MongoRepository<Tickets, String> {
//    List<Flight> findBySourceAndDestination(String source, String destination);
    // Get upcoming flights for a user
    List<Tickets> findByFlightIdAndDepartureTimeAfterOrderByDepartureTimeAsc(String flightId, LocalDateTime now);

    // Get past flights for a user
    List<Tickets> findByFlightIdAndDepartureTimeBeforeOrderByDepartureTimeDesc(String flightId, LocalDateTime now);

    List<Tickets> findByDepartureAndDestination(String departure, String destination);
}
