package com.Infotrixs.Payroll_System.Services;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.DueSalaryDetails;

public interface EmployeeService {
    String passwordResetRequest(int empId);

    String resetPassword(int empId, String otp, String newPassword);

    DueSalaryDetails seeDueSalaryDetails(int empId);
}
