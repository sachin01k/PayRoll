package com.Infotrixs.Payroll_System.Controllers;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.AllPaymentRecords;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.DueSalaryDetails;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.EmployeeDetails;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.SalaryReplica;
import com.Infotrixs.Payroll_System.Services.Impl.AdminServiceImpl;
import com.Infotrixs.Payroll_System.Services.Impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    final EmployeeServiceImpl employeeService;
    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/password-reset-request/employee-id/{employeeId}")
    public ResponseEntity passwordResetRequest(@PathVariable("employeeId") int empId){
        try{
            String response = employeeService.passwordResetRequest(empId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/reset-password/employee-id/{employeeId}/otp/{otp}/new-password/{newPassword}")
    public ResponseEntity resetPassword(@PathVariable("employeeId") int empId, @PathVariable("otp") String otp, @PathVariable("newPassword") String newPassword){
        try{
            String response = employeeService.resetPassword(empId, otp, newPassword);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/see-due-salary-details/employee-id/{employeeId}")
    public ResponseEntity seeDueSalaryDetails(@PathVariable("employeeId") int empId){
        try{
            DueSalaryDetails response = employeeService.seeDueSalaryDetails(empId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/see-payment-records/employee-id/{employeeId}")
    public ResponseEntity seePaymentRecords(@PathVariable("employeeId") int empId){
        try{
            AllPaymentRecords response = employeeService.seePaymentRecords(empId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/see-salary-structure/employee-id/{employeeId}")
    public ResponseEntity seeSalaryStructure(@PathVariable("employeeId") int empId){
        try{
            SalaryReplica response = employeeService.seeSalaryStructure(empId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/see-account-details/employee-id/{employeeId}")
    public ResponseEntity seeAccountDetails(@PathVariable("employeeId") int empId){
        try{
            EmployeeDetails response = employeeService.seeAccountDetails(empId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
