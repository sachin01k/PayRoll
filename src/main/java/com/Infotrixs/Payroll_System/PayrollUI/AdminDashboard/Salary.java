/*
 * Salary class represents the salary details of an employee.
 * It includes various attributes such as salary ID, employee ID, employee name, and different components of the salary.
 * The class provides getters and setters for each attribute.
 */

package com.Infotrixs.Payroll_System.PayrollUI.AdminDashboard;

public class Salary {
    private int salaryId;
    private int employeeId;
    private String employeeName;
    private double base;
    private double houseRentAllow;
    private double convenienceAllow;
    private double insurance;
    private double inHand;
    private double previousDues;
    private double deductionForUnpaidLeaves;
    private int unpaidLeaves;
    private double netPay;
    private String paymentStatus;
    private String department;
    private String employmentLevel;
    private String designation;
    private double pf;

    /*
     * Parameterized constructor to initialize the Salary object with provided values.
     */
    public Salary(int salaryId, int employeeId, String employeeName, double base, double houseRentAllow, double convenienceAllow, double insurance, double inHand, double previousDues, double deductionForUnpaidLeaves, int unpaidLeaves, double netPay, String paymentStatus, String department, String employmentLevel, String designation, double pf) {
        this.salaryId = salaryId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.base = base;
        this.houseRentAllow = houseRentAllow;
        this.convenienceAllow = convenienceAllow;
        this.insurance = insurance;
        this.inHand = inHand;
        this.previousDues = previousDues;
        this.deductionForUnpaidLeaves = deductionForUnpaidLeaves;
        this.unpaidLeaves = unpaidLeaves;
        this.netPay = netPay;
        this.paymentStatus = paymentStatus;
        this.department = department;
        this.employmentLevel = employmentLevel;
        this.designation = designation;
        this.pf = pf;
    }

    /*
     * Default constructor.
     */
    public Salary() {
    }

    // Getters and Setters for each attribute

    public int getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(int salaryId) {
        this.salaryId = salaryId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getHouseRentAllow() {
        return houseRentAllow;
    }

    public void setHouseRentAllow(double houseRentAllow) {
        this.houseRentAllow = houseRentAllow;
    }

    public double getConvenienceAllow() {
        return convenienceAllow;
    }

    public void setConvenienceAllow(double convenienceAllow) {
        this.convenienceAllow = convenienceAllow;
    }

    public double getInsurance() {
        return insurance;
    }

    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }

    public double getInHand() {
        return inHand;
    }

    public void setInHand(double inHand) {
        this.inHand = inHand;
    }

    public double getPreviousDues() {
        return previousDues;
    }

    public void setPreviousDues(double previousDues) {
        this.previousDues = previousDues;
    }

    public double getDeductionForUnpaidLeaves() {
        return deductionForUnpaidLeaves;
    }

    public void setDeductionForUnpaidLeaves(double deductionForUnpaidLeaves) {
        this.deductionForUnpaidLeaves = deductionForUnpaidLeaves;
    }

    public int getUnpaidLeaves() {
        return unpaidLeaves;
    }

    public void setUnpaidLeaves(int unpaidLeaves) {
        this.unpaidLeaves = unpaidLeaves;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmploymentLevel() {
        return employmentLevel;
    }

    public void setEmploymentLevel(String employmentLevel) {
        this.employmentLevel = employmentLevel;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPf() {
        return pf;
    }

    public void setPf(double pf) {
        this.pf = pf;
    }
}