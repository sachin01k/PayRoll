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

    /**
     * This function takes an Admins username and password as authentication credentials,
     * matches it with the username and password corresponding to that admin and returns
     * name and Admin ID of that employee.
     * @return DTO AdminLoginDetails which has two fields; Employee's name and ID.
     *
     * The function takes Admins username and password, Spring security validates it. If the
     * credentials are valid, the function extracts Employee using the username from the DB. Then
     * it copies Admin name and ID into DTO AdminLoginDetails and send to user.
     * @return
     */
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
