package com.Infotrixs.Payroll_System.Configurations;

import com.Infotrixs.Payroll_System.Models.Admin;
import com.Infotrixs.Payroll_System.Models.Employee;
import com.Infotrixs.Payroll_System.Repositories.AdminRepository;
import com.Infotrixs.Payroll_System.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findByUsername(username);
        Admin admin = adminRepository.findByUsername(username);
        if(employee == null && admin == null){
            throw new UsernameNotFoundException("Invalid username.");
        }
        if(employee == null){
            return new UserDetailCreatorForAdmin(admin);
        } else {
            return new UserDetailCreatorForEmployee(employee);
        }
    }
}
