package com.Infotrixs.Payroll_System.Services.Impl;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.EmployeeLoginDetails;
import com.Infotrixs.Payroll_System.Enums.AccountAccess;
import com.Infotrixs.Payroll_System.Exceptions.AccountAccessRestrictedException;
import com.Infotrixs.Payroll_System.Exceptions.InvalidInputException;
import com.Infotrixs.Payroll_System.Models.Employee;
import com.Infotrixs.Payroll_System.Repositories.EmployeeRepository;
import com.Infotrixs.Payroll_System.Services.EmployeeLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeLoginServiceImpl implements EmployeeLoginService {
    final EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeLoginServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeLoginDetails employeeLogin() {
        // extract employee username from the authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw  new InvalidInputException("Invalid username or password.");
        }
        String employeeUsername = authentication.getName();
        // get employee
        Employee employee = employeeRepository.findByUsername(employeeUsername);
        // validate employee access
        if(employee.getAccess().equals(AccountAccess.RESTRICTED)){
            throw new AccountAccessRestrictedException("You account access is restricted.");
        }
        // return name and ID
        return EmployeeLoginDetails.builder()
                .employeeId(employee.getEmpId())
                .employeeName(employee.getName())
                .build();
    }
}
