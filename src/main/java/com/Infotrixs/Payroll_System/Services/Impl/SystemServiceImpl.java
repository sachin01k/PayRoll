package com.Infotrixs.Payroll_System.Services.Impl;

import com.Infotrixs.Payroll_System.DTOs.NewAdminRequest;
import com.Infotrixs.Payroll_System.Exceptions.InvalidAdminAuthTokenException;
import com.Infotrixs.Payroll_System.Models.Admin;
import com.Infotrixs.Payroll_System.Repositories.AdminRepository;
import com.Infotrixs.Payroll_System.Services.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {
    final AdminRepository adminRepository;
    final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public SystemServiceImpl(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String addNewAdmin(NewAdminRequest request) {
        // validate if request has a valid authorization token
        if(!request.getAuthToken().equals("AUTHORIZED_ADMIN0542")){
            throw new InvalidAdminAuthTokenException("Invalid Admin Authorization Token");
        }
        // if authorization token is valid, create a new admin and save in DB
        Admin admin = Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        Admin savedAdmin = adminRepository.save(admin);
        return "Admin registered";
    }
}
