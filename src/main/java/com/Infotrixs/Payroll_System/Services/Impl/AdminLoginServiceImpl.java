package com.Infotrixs.Payroll_System.Services.Impl;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.AdminLoginDetails;
import com.Infotrixs.Payroll_System.Exceptions.InvalidInputException;
import com.Infotrixs.Payroll_System.Models.Admin;
import com.Infotrixs.Payroll_System.Repositories.AdminRepository;
import com.Infotrixs.Payroll_System.Services.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    final AdminRepository adminRepository;
    final SecurityContextHolder contextHolder;
    @Autowired
    public AdminLoginServiceImpl(AdminRepository adminRepository, SecurityContextHolder contextHolder) {
        this.adminRepository = adminRepository;
        this.contextHolder = contextHolder;
    }

    @Override
    public AdminLoginDetails adminLogin() {
        // extract employee username from the authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw  new InvalidInputException("Invalid username or password.");
        }
        String adminUsername = authentication.getName();
        // get admin
        Admin admin = adminRepository.findByUsername(adminUsername);
        // return the admin name and ID
        return AdminLoginDetails.builder()
                .adminId(admin.getAdminId())
                .adminName(admin.getName())
                .build();
    }
}
