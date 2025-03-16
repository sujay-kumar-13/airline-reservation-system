package com.horizonFlights.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "admin")
public class Admin {
    @Id
    private String id;

    private String email;
    private String username;
    private String password;

    public Admin() {};
}
