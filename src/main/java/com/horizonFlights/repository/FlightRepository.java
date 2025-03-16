package com.horizonFlights.repository;

import com.horizonFlights.model.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends MongoRepository<Flight, String> {


    default List<Flight> findByOriginAndDestinationAndDepartureDate(String origin, String destination, LocalDate departureDate) {
        LocalDateTime startOfDay = departureDate.atStartOfDay();
        LocalDateTime endOfDay = departureDate.atTime(23, 59, 59);

        return findByOriginAndDestinationAndDepartureTimeBetween(origin, destination, startOfDay, endOfDay);
    }

    List<Flight> findByOriginAndDestinationAndDepartureTimeBetween(String origin, String destination, LocalDateTime start, LocalDateTime end);
}
