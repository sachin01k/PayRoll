package com.Infotrixs.Payroll_System.Services;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.AllPaymentRecords;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.DueSalaryDetails;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.EmployeeDetails;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.SalaryReplica;

public interface EmployeeService {
    String passwordResetRequest(int empId);
    String resetPassword(int empId, String otp, String newPassword);
    DueSalaryDetails seeDueSalaryDetails(int empId);
    AllPaymentRecords seePaymentRecords(int empId);
    SalaryReplica seeSalaryStructure(int empId);
    EmployeeDetails seeAccountDetails(int empId);
}
