package com.horizonFlights.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Document(collection = "flights")
public class Flight {

    @Id
    private String id;

    private String flightNumber;
    private String origin;
    private String destination;

    private LocalDate departureDate;  // Changed from LocalDateTime
    private LocalTime departureTime;  // New field
    private LocalDate arrivalDate;    // Changed from LocalDateTime
    private LocalTime arrivalTime;    // New field

    private double economyClassPrice;
    private double businessClassPrice;
    private double firstClassPrice;

    private List<Integer> economySeats;
    private List<Integer> businessSeats;
    private List<Integer> firstClassSeats;

    public Flight() {}

    public Flight(String flightNumber, String origin, String destination,
                  LocalDate departureDate, LocalTime departureTime,
                  LocalDate arrivalDate, LocalTime arrivalTime,
                  double economyClassPrice, double businessClassPrice, double firstClassPrice,
                  List<Integer> economySeats, List<Integer> businessSeats, List<Integer> firstClassSeats) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.economyClassPrice = economyClassPrice;
        this.businessClassPrice = businessClassPrice;
        this.firstClassPrice = firstClassPrice;
        this.economySeats = economySeats;
        this.businessSeats = businessSeats;
        this.firstClassSeats = firstClassSeats;
    }
}
