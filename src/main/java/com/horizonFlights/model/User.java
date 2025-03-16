//package com.horizonFlights.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "users") // Ensure it matches your MySQL table name
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, unique = true)
//    private String username;
//
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Column(nullable = true)
//    private String role;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Flight> flights;
//
//    // Constructors
//    public User() {}
//
//    public User(String username, String email, String password) {
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        role = "user";
//    }
//}

package com.horizonFlights.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "users") // MongoDB Collection Name
public class User {

    @Id
    private String id;  // MongoDB uses String IDs (ObjectId)

    private String username;
    private String email;
    private String password;
//    private String role;

    // Store a list of flight IDs instead of full Flight objects
    private List<Tickets> tickets;

    // Constructors
    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
//        this.role = "user";
    }
}
