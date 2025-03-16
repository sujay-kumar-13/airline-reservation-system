package com.horizonFlights.service;

import com.horizonFlights.model.Flight;
import com.horizonFlights.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

//    @Autowired
//    private BookedRepository bookedRepository;

    // ✅ Add a flight to the database
    public void addFlight(Flight flight) {
        flightRepository.save(flight);
    }

    // ✅ Fetch all flights from the database
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Searches for flights for user
    public List<Flight> searchFlights(String origin, String destination, LocalDate departureDate) {
        return flightRepository.findByOriginAndDestinationAndDepartureDate(origin, destination, departureDate);
    }
}
