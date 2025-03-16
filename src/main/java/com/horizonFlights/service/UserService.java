package com.horizonFlights.service;

import com.horizonFlights.model.User;
import com.horizonFlights.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // In your UserService
    public void testSaveUser() {
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("testPassword");
//        testUser.setRole("USER");
        userRepository.save(testUser);
    }

    //In your controller
    @GetMapping("/testSave")
    public String testSave(){
        testSaveUser();
        return "booking";
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user;
    }
}