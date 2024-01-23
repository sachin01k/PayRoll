package com.Infotrixs.Payroll_System.Services.Impl;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.*;
import com.Infotrixs.Payroll_System.Enums.AccountAccess;
import com.Infotrixs.Payroll_System.Exceptions.AccountAccessRestrictedException;
import com.Infotrixs.Payroll_System.Exceptions.EmployeeNotFoundException;
import com.Infotrixs.Payroll_System.Exceptions.InvalidOTPException;
import com.Infotrixs.Payroll_System.Exceptions.NoRecordsFoundException;
import com.Infotrixs.Payroll_System.Models.Employee;
import com.Infotrixs.Payroll_System.Models.PaySlip;
import com.Infotrixs.Payroll_System.Models.Salary;
import com.Infotrixs.Payroll_System.Repositories.EmployeeRepository;
import com.Infotrixs.Payroll_System.Services.EmployeeService;
import com.Infotrixs.Payroll_System.Utility.Converter;
import com.Infotrixs.Payroll_System.Utility.KeyGenerator;
import com.Infotrixs.Payroll_System.Utility.MailComposer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    final EmployeeRepository employeeRepository;
    final JavaMailSender javaMailSender;
    final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, JavaMailSender javaMailSender, BCryptPasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String passwordResetRequest(int empId) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        // validate employee access
        if(employeeOptional.get().getAccess().equals(AccountAccess.RESTRICTED)){
            throw new AccountAccessRestrictedException("You account access is restricted.");
        }
        // if employee is valid, generate a unique key for validation
        String uniqueId = KeyGenerator.generateUniqueKey();
        Employee employee = employeeOptional.get();
        employee.setPasswordResetReqId(uniqueId);
        employeeRepository.save(employee);
        try{
            // send this unique id at Admin email
            SimpleMailMessage message = MailComposer.composeMailConsistingPasswordResetIdForEmployee(employee);
            javaMailSender.send(message);
        } catch (Exception e){
            throw new RuntimeException("Some error occurred while generating OTP. Try again.");
        }

        return "An OTP was sent to your email. Use the OTP to reset your password";
    }

    @Override
    public String resetPassword(int empId, String otp, String newPassword) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        // validate employee access
        if(employeeOptional.get().getAccess().equals(AccountAccess.RESTRICTED)){
            throw new AccountAccessRestrictedException("You account access is restricted.");
        }
        Employee employee = employeeOptional.get();
        // validate OTP
        if(employee.getPasswordResetReqId() == null) {
            throw new InvalidOTPException("Generate OTP to change password");
        }
        if(!employee.getPasswordResetReqId().equals(otp)){
            throw new InvalidOTPException("Invalid OTP");
        }
        // if otp is valid, update email and reset password req id
        employee.setPassword(passwordEncoder.encode(newPassword));
        employee.setPasswordResetReqId(null);
        employeeRepository.save(employee);

        return "Password was changed.";
    }

    @Override
    public DueSalaryDetails seeDueSalaryDetails(int empId) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        // validate employee access
        if(employeeOptional.get().getAccess().equals(AccountAccess.RESTRICTED)){
            throw new AccountAccessRestrictedException("You account access is restricted.");
        }
        // get employee
        Employee employee = employeeOptional.get();
        Salary salary = employee.getSalary();

        // prepare due salary details to send as response
        return Converter.prepareDueSalaryDetails(employee, salary);
    }

    @Override
    public AllPaymentRecords seePaymentRecords(int empId){
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        // validate employee access
        if(employeeOptional.get().getAccess().equals(AccountAccess.RESTRICTED)){
            throw new AccountAccessRestrictedException("You account access is restricted.");
        }
        // get all previous payment records of the employee
        Employee employee = employeeOptional.get();
        List<PaySlip> paySlipList = employee.getPayslips();
        if(paySlipList.isEmpty()){
            throw new NoRecordsFoundException("No previous payment records were found.");
        }
        // iterate over employee's payslip and create their replicas to send to the user
        List<PaySlipReplica> paySlipReplicaList = new ArrayList<>();
        for(PaySlip paySlip : paySlipList){
            PaySlipReplica paySlipReplica = Converter.preparePaySlipReplica(paySlip);
            paySlipReplicaList.add(paySlipReplica);
        }
        // add payslip replica list to all payment records and send it to user
        return AllPaymentRecords.builder()
                .paymentRecords(paySlipReplicaList)
                .build();
    }

    @Override
    public SalaryReplica seeSalaryStructure(int empId) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        // validate employee access
        if(employeeOptional.get().getAccess().equals(AccountAccess.RESTRICTED)){
            throw new AccountAccessRestrictedException("You account access is restricted.");
        }
        Employee employee = employeeOptional.get();
        // prepare a salary replica to send to the user
        return Converter.prepareSalaryReplica(employee);
    }

    @Override
    public EmployeeDetails seeAccountDetails(int empId) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        // validate employee access
        if(employeeOptional.get().getAccess().equals(AccountAccess.RESTRICTED)){
            throw new AccountAccessRestrictedException("You account access is restricted.");
        }
        // get employee and prepare Employee Details to send to user
        Employee employee = employeeOptional.get();
        return Converter.prepareEmployeeDetails(employee);
    }
}
