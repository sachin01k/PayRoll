package com.Infotrixs.Payroll_System.Controllers;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.AdminLoginDetails;
import com.Infotrixs.Payroll_System.Services.Impl.AdminLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-login")
public class AdminLoginController {
    final AdminLoginServiceImpl adminLoginService;
    @Autowired
    public AdminLoginController(AdminLoginServiceImpl adminLoginService) {
        this.adminLoginService = adminLoginService;
    }

    @GetMapping
    public ResponseEntity adminLogin(){
        try{
            AdminLoginDetails response = adminLoginService.adminLogin();
            return new ResponseEntity(response, HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
