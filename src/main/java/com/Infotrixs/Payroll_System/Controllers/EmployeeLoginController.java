package com.Infotrixs.Payroll_System.Controllers;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.AdminLoginDetails;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.EmployeeLoginDetails;
import com.Infotrixs.Payroll_System.Services.Impl.EmployeeLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee-login")
public class EmployeeLoginController {
    final EmployeeLoginServiceImpl employeeLoginService;
    @Autowired
    public EmployeeLoginController(EmployeeLoginServiceImpl employeeLoginService) {
        this.employeeLoginService = employeeLoginService;
    }

    @GetMapping
    public ResponseEntity employeeLogin(){
        try{
            EmployeeLoginDetails response = employeeLoginService.employeeLogin();
            return new ResponseEntity(response, HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
