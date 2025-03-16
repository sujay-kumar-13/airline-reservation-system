//package com.horizonFlights.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "flights")
//public class Flight {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String flightNumber;
//    private String departure;
//    private String destination;
//    private LocalDateTime departureTime;
//
////    private Long id;
////
////    private String airline;
////    private String source;
////    private String destination;
////    private LocalDateTime departureTime;
////    private LocalDateTime arrivalTime;
////    private int availableSeats;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false) // Make sure this matches your database column
//    private User user;
//
//    // Getters and Setters
//}

package com.horizonFlights.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "tickets") // MongoDB Collection Name
public class Tickets {
    @Id
    private String id;  // MongoDB uses String for IDs

//    @Field("user_id")
//    private String userId;
    private String flightId;
    private String flightNumber;
    private String departure;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private String selectedClass;
    private List<Passengers> passengers;
    private String status;

//    private String userId; // Instead of @ManyToOne User reference, store only userId

    // Constructors
    public Tickets() {}

    public Tickets(String id, String flightNumber, String departure, String destination, LocalDate departureDate, LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime, String selectedClass, List<Passengers> passengers) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.selectedClass = selectedClass;
        this.passengers = passengers;
    }
}
