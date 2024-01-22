package com.Infotrixs.Payroll_System.Utility;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.*;
import com.Infotrixs.Payroll_System.Enums.PaymentStatus;
import com.Infotrixs.Payroll_System.Models.Admin;
import com.Infotrixs.Payroll_System.Models.Employee;
import com.Infotrixs.Payroll_System.Models.PaySlip;
import com.Infotrixs.Payroll_System.Models.Salary;

public class Converter {

    public static DueSalaryDetails prepareDueSalaryDetails(Employee employee, Salary salary) {
        float totalEarning = salary.getInHand() + employee.getPrevDue();
        float deductionForUnpaidLeaves = deductionForUnpaidLeaves(employee.getUnpaidLeaves(), salary.getBase());
        float netPay = totalEarning - deductionForUnpaidLeaves;
        return DueSalaryDetails.builder()
                .salaryId(salary.getSalaryId())
                .employeeId(employee.getEmpId())
                .employeeName(employee.getName())
                .base(salary.getBase())
                .houseRentAllow(salary.getHouseRentAllow())
                .convenienceAllow(salary.getConvenienceAllow())
                .PF(salary.getPF())
                .insurance(salary.getInsurance())
                .previousDues(employee.getPrevDue())
                .unpaidLeaves(employee.getUnpaidLeaves())
                .inHand(salary.getInHand())
                .deductionForUnpaidLeaves(deductionForUnpaidLeaves)
                .netPay(netPay)
                .paymentStatus(String.valueOf(PaymentStatus.UNPAID))
                .department(String.valueOf(employee.getDepartment()))
                .employmentLevel(String.valueOf(employee.getEmploymentLevel()))
                .designation(employee.getDesignation())
                .build();
    }

    private static float deductionForUnpaidLeaves(int unpaidLeaves, float baseSalary) {
        // divide base salary with number of days in a month
        float perDaySalary = baseSalary/30;
        // total deduction
        return perDaySalary * unpaidLeaves;
    }

    public static PaySlip preparePaySlip(Employee employee) {
        // get employee salary
        Salary salary = employee.getSalary();
        float deductionForUnpaidLeaves = deductionForUnpaidLeaves(employee.getUnpaidLeaves(), salary.getBase());

        // generate a payslip
        return PaySlip.builder()
                .employeeId(employee.getEmpId())
                .employeeName(employee.getName())
                .department(employee.getDepartment())
                .employmentLevel(employee.getEmploymentLevel())
                .designation(employee.getDesignation())
                .base(salary.getBase())
                .houseRestAllow(salary.getHouseRentAllow())
                .convenienceAllow(salary.getConvenienceAllow())
                .pf(salary.getPF())
                .insurance(salary.getInsurance())
                .inHand(salary.getInHand())
                .unpaidLeaves(employee.getUnpaidLeaves())
                .deductionForUnpaidLeaves(deductionForUnpaidLeaves)
                .prevDue(employee.getPrevDue())
                .paymentStatus(PaymentStatus.PAID)
                .employee(employee)
                .build();
    }

    public static PaySlipReplica preparePaySlipReplica(PaySlip paySlip) {
        return PaySlipReplica.builder()
                .paySlipId(paySlip.getPaySlipId())
                .employeeId(paySlip.getEmployeeId())
                .employeeName(paySlip.getEmployeeName())
                .department(String.valueOf(paySlip.getDepartment()))
                .employmentLevel(String.valueOf(paySlip.getEmploymentLevel()))
                .designation(paySlip.getDesignation())
                .base(paySlip.getBase())
                .houseRestAllow(paySlip.getHouseRestAllow())
                .convenienceAllow(paySlip.getConvenienceAllow())
                .pf(paySlip.getPf())
                .insurance(paySlip.getInsurance())
                .inHand(paySlip.getInHand())
                .prevDue(paySlip.getPrevDue())
                .deductionForUnpaidLeaves(paySlip.getDeductionForUnpaidLeaves())
                .unpaidLeaves(paySlip.getUnpaidLeaves())
                .prevDue(paySlip.getPrevDue())
                .paymentStatus(String.valueOf(paySlip.getPaymentStatus()))
                .build();
    }

    public static SalaryReplica prepareSalaryReplica(Employee employee) {
        // get employee's salary
        Salary salary = employee.getSalary();
        // prepare a salary replica to send to the user
        return SalaryReplica.builder()
                .employeeName(employee.getName())
                .salaryId(salary.getSalaryId())
                .base(salary.getBase())
                .houseRentAllow(salary.getHouseRentAllow())
                .convenienceAllow(salary.getConvenienceAllow())
                .PF(salary.getPF())
                .insurance(salary.getInsurance())
                .inHand(salary.getInHand())
                .build();
    }

    public static AdminDetails prepareAdminDetails(Admin admin) {
        return AdminDetails.builder()
                .adminId(admin.getAdminId())
                .name(admin.getName())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .username(admin.getUsername())
                .build();
    }

    public static EmployeeDetails prepareEmployeeDetails(Employee employee) {
        return EmployeeDetails.builder()
                .empId(employee.getEmpId())
                .name(employee.getName())
                .phone(employee.getPhone())
                .email(employee.getEmail())
                .username(employee.getUsername())
                .department(String.valueOf(employee.getDepartment()))
                .employmentLevel(String.valueOf(employee.getEmploymentLevel()))
                .designation(employee.getDesignation())
                .bankAccNo(employee.getBankAccNo())
                .ifsc(employee.getIfsc())
                .accountAccess(String.valueOf(employee.getAccess()))
                .build();
    }
}
