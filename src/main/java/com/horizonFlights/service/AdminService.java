package com.horizonFlights.service;

import com.horizonFlights.model.Admin;
import com.horizonFlights.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin authenticate(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        return admin;
    }
}
