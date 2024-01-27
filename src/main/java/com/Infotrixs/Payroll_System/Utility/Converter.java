package com.Infotrixs.Payroll_System.Utility;

import com.Infotrixs.Payroll_System.DTOs.Outgoing.*;
import com.Infotrixs.Payroll_System.Enums.PaymentStatus;
import com.Infotrixs.Payroll_System.Models.Admin;
import com.Infotrixs.Payroll_System.Models.Employee;
import com.Infotrixs.Payroll_System.Models.PaySlip;
import com.Infotrixs.Payroll_System.Models.Salary;

public class Converter {
    /**
     * This function creates and object of DTO DueSalaryDetails by extracting relevant details from
     * Employee and Salary model classes.
     * @param employee
     * @param salary
     * @return DTO DueSalaryDetails which has fields related to Employee and Salary model classes.
     */
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

    /**
     * This function calculates and returns the salary deduction (amount) made by employer against the
     * unpaid leaves taken by the employee.
     * @param unpaidLeaves
     * @param baseSalary
     * @return a float value indicating the total deduction amount.
     *
     * Read the one-liner comments in the function to see the rules of deduction.
     */
    private static float deductionForUnpaidLeaves(int unpaidLeaves, float baseSalary) {
        // divide base salary with number of days in a month
        float perDaySalary = baseSalary/30;
        // total deduction
        return perDaySalary * unpaidLeaves;
    }

    /**
     * This function creates an object of PaySlip Model class by extracting the data from Employee
     * and Salary model classes.
     * @param employee
     * @return Model PaySlip which has fields related to Employee and Salary model classes.
     */
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

    /**
     * This function creates an object of DTO PaySlipReplica by copying the fields from PaySlip model class.
     * @param paySlip
     * @return DTO PaySlipReplica. Refer PaySlip in models and PaySlipReplica in DTOs to see the fields.
     */
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

    /**
     * This function creates an object of DTO SalaryReplica by extracting the values from an employee's Salary (Salary model class).
     * @param employee
     * @return DTO SalaryReplica which has fields similar to Salary model class. Refer to these classes to see the fields.
     */
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

    /**
     * This function creates an object of DTO AdminDetails by copying the fields from Admin model class.
     * @param admin
     * @return DTO AdminDetails which has two fields similar to Admin model class.
     */
    public static AdminDetails prepareAdminDetails(Admin admin) {
        return AdminDetails.builder()
                .adminId(admin.getAdminId())
                .name(admin.getName())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .username(admin.getUsername())
                .build();
    }

    /**
     * This function creates an object of DTO EmployeeDetails by copying the fields from Employee model class.
     * @param employee
     * @return DTO EmployeeDetails which has fields similar to Employee model class.
     */
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
