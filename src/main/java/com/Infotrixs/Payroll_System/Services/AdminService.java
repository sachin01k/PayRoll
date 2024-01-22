package com.Infotrixs.Payroll_System.Services;

import com.Infotrixs.Payroll_System.DTOs.Incoming.NewAdminRequest;
import com.Infotrixs.Payroll_System.DTOs.Incoming.NewEmployeeRequest;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.*;
import com.Infotrixs.Payroll_System.Enums.Department;
import com.Infotrixs.Payroll_System.Enums.EmploymentLevel;

public interface AdminService {
    String addNewAdmin(NewAdminRequest newAdminRequest);
    String updateEmail(int adminId, String newEmail);
    String updatePhone(int adminId, String newPhone);
    String updateUsername(int adminId, String newUsername);
    String passwordResetRequest(int adminId);
    String resetPassword(int adminId, String otp, String newPassword);
    String addNewEmployee(NewEmployeeRequest request);
    String updateDepartment(int empId, Department newDepartment);
    String updateEmploymentLevel(int empId, EmploymentLevel newLevel);
    String updateEmployeeAccess(int empId);
    String recordUnpaidLeave(int empId, int leaves);
    DueSalaryDetails seeDueSalaryOfAnEmployee(int empId);
    String recordPreviousDueOfAnEmployee(int empId, float amount);
    PaySlipReplica paySalaryAndGeneratePaySlipOfAnEmployee(int empId);
    SalaryReplica seeSalaryStructureOfAnEmployee(int empId);
    AllDueSalaries seeDueSalaries();
    AllPaymentRecords seePaymentRecordsOfAnEmployee(int empId);
    AllPaymentRecords seePaymentRecords();
    String deleteAnEmployee(int empId);
    AdminDetails seeAccountDetails(int adminId);
}
