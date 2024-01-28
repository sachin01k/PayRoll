package com.Infotrixs.Payroll_System.Services.Impl;

import com.Infotrixs.Payroll_System.DTOs.Incoming.NewAdminRequest;
import com.Infotrixs.Payroll_System.DTOs.Incoming.NewEmployeeRequest;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.*;
import com.Infotrixs.Payroll_System.Enums.AccountAccess;
import com.Infotrixs.Payroll_System.Enums.Department;
import com.Infotrixs.Payroll_System.Enums.EmploymentLevel;
import com.Infotrixs.Payroll_System.Exceptions.*;
import com.Infotrixs.Payroll_System.Models.Admin;
import com.Infotrixs.Payroll_System.Models.Employee;
import com.Infotrixs.Payroll_System.Models.PaySlip;
import com.Infotrixs.Payroll_System.Models.Salary;
import com.Infotrixs.Payroll_System.Repositories.AdminRepository;
import com.Infotrixs.Payroll_System.Repositories.EmployeeRepository;
import com.Infotrixs.Payroll_System.Repositories.PaySlipRepository;
import com.Infotrixs.Payroll_System.Repositories.SalaryRepository;
import com.Infotrixs.Payroll_System.Services.AdminService;
import com.Infotrixs.Payroll_System.Utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    final String adminAuthorizationToken = "AUTHORIZED_ADMIN0542";
    final AdminRepository adminRepository;
    final SalaryRepository salaryRepository;
    final EmployeeRepository employeeRepository;
    final PaySlipRepository paySlipRepository;
    final BCryptPasswordEncoder passwordEncoder;
    final JavaMailSender javaMailSender;
    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, SalaryRepository salaryRepository, EmployeeRepository employeeRepository, PaySlipRepository paySlipRepository, BCryptPasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.adminRepository = adminRepository;
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
        this.paySlipRepository = paySlipRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    /**
     *  A new Admin Account is added by using Authorized Token. Authorization token is verified before
     *  adding the new employee to system, if token verification fails then InvalidAdminAuthTokenException
     *  is thrown. Admin class object is used to store the admin details and adminRepository object is used
     *  to store the admin details.
     * @param request
     * @return Admin is saved in Database successfully String is Returned "Admin Registered".
     */
    @Override
    public String addNewAdmin(NewAdminRequest request) {
        // validate if request has a valid authorization token
        if(!request.getAuthToken().equals(adminAuthorizationToken)){
            throw new InvalidAdminAuthTokenException("Invalid Admin Authorization Token");
        }
        // if authorization token is valid, create a new admin and save in DB
        Admin admin = Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Admin savedAdmin = adminRepository.save(admin);
        return "Admin registered.";
    }

    /**
     * AdminController will call this method and will send parameters of admin
     * whose email is to be updated. Firstly admin id is searched so to update email.
     * In case if no such admin is found then AdminNotFoundException is thrown.
     * And if admin is found the admin email is updated and saved in DataBase using Dao.
     * @param adminId
     * @param newEmail
     * @return String is returned if Email is Successfully updated "Email was updated".
     */
    @Override
    public String updateEmail(int adminId, String newEmail) {
        // validate admin
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if(adminOptional.isEmpty()){
            throw new AdminNotFoundException("Admin does not exists.");
        }
        // update email
        Admin admin = adminOptional.get();
        admin.setEmail(newEmail);
        adminRepository.save(admin);

        return "Email was updated.";
    }

    /**
     * Admin Phone Number is updated in below method
     * Firstly admin id is verified in database to update email.
     * In case if no such admin is found then AdminNotFoundException is thrown.
     * And if admin is found the admin phone number is updated and saved in DataBase using Dao.     *
     * @param adminId
     * @param newPhone
     * @return String is returned if phone number is Successfully updated "Phone number was updated".
     */
    @Override
    public String updatePhone(int adminId, String newPhone) {
        // validate admin
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if(adminOptional.isEmpty()){
            throw new AdminNotFoundException("Admin does not exists.");
        }
        // update phone
        Admin admin = adminOptional.get();
        admin.setPhone(newPhone);
        adminRepository.save(admin);

        return "Phone number was updated.";
    }

    /**
     * This Function update usernmae of admin and if admin id is not found in database
     * AdminNotFoundException is thrown. If admin id is verified then uaername is updated.
     * @param adminId
     * @param newUsername
     * @return String is returned if username is updated "Username is changed."
     */
    @Override
    public String updateUsername(int adminId, String newUsername) {
        // validate admin
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if(adminOptional.isEmpty()){
            throw new AdminNotFoundException("Admin does not exists.");
        }
        // update username
        Admin admin = adminOptional.get();
        admin.setUsername(newUsername);
        adminRepository.save(admin);

        return "Username was changed.";
    }

    /**
     * In this function admin request to reset password and otp is sent to the verified email id.
     * The otp is temporarily stored in database.
     * @param adminId
     * @return String is Returned if admin is verified in database "An OTP was sent to your email. Use the OTP to reset your password."
     */
    @Override
    public String passwordResetRequest(int adminId) {
        // validate admin
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if(adminOptional.isEmpty()){
            throw new AdminNotFoundException("Admin does not exists.");
        }
        // if admin is valid, generate a unique key for validation
        String uniqueId = KeyGenerator.generateUniqueKey();
        Admin admin = adminOptional.get();
        admin.setPasswordResetReqId(uniqueId);
        adminRepository.save(admin);
        try{
            // send this unique id at Admin email
            SimpleMailMessage message = MailComposer.composeMailConsistingPasswordResetIdForAdmin(admin);
            javaMailSender.send(message);
        } catch (Exception e){
            throw new RuntimeException("Some error occurred while generating OTP. Try again.");
        }

        return "An OTP was sent to your email. Use the OTP to reset your password";
    }

    /**
     * In this function otp is verified and in case if otp is invalid then InvalidOTPException
     * if otp is verified then the new password is reset in database.
     * @param adminId
     * @param otp
     * @param newPassword
     * @return After resetting password String is returned "Password was changed."
     * @throws InvalidOTPException
     */
    @Override
    public String resetPassword(int adminId, String otp, String newPassword) throws InvalidOTPException {
        // validate admin
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if(adminOptional.isEmpty()){
            throw new AdminNotFoundException("Admin does not exists.");
        }
        Admin admin = adminOptional.get();
        // validate OTP
        if(admin.getPasswordResetReqId() == null) {
            throw new InvalidOTPException("Generate OTP to change password");
        }
        if(!admin.getPasswordResetReqId().equals(otp)){
            throw new InvalidOTPException("Invalid OTP");
        }
        // if otp is valid, update email and reset password req id
        admin.setPassword(passwordEncoder.encode(newPassword));
        admin.setPasswordResetReqId(null);
        adminRepository.save(admin);

        return "Password was changed.";
    }

    /**
     * In this function admin can add/register new Employee hired in the organisation.
     * After the registering employee, an email is sent to employee registered email with details.
     *
     * @param request
     * @return after registering the employee String "Employee was registered with employee ID." with
     * system generated employee ID.
     */
    @Override
    public String addNewEmployee(NewEmployeeRequest request) {
        String password = request.getPassword();
        // Define employee's designation based on department and employment level
        String designation = defineDesignation(request.getDepartment(), request.getEmploymentLevel());
        // create a new Employee
        Employee employee = Employee.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .department(request.getDepartment())
                .employmentLevel(request.getEmploymentLevel())
                .access(AccountAccess.GRANTED)
                .designation(designation)
                .bankAccNo(request.getBankAccNo())
                .ifsc(request.getIfsc())
                .build();
        // Save employee
        Employee savedEmployee = employeeRepository.save(employee);
        // create a new salary according to the department & employment level based on predefined rules
        float baseMonthlySalary = SalaryCalculator.getBaseMonthlySalaryBasedOnDepartmentAndRole(savedEmployee.getDepartment(), savedEmployee.getEmploymentLevel());
        Salary salary = SalaryCalculator.generateNewMonthlySalary(savedEmployee, baseMonthlySalary);
        // save salary
        Salary savedSalary = salaryRepository.save(salary);
        // add salary to employee
        savedEmployee.setSalary(savedSalary);
        try{
            // compose mail to send employee with employee details
            SimpleMailMessage message = MailComposer.composeMailConsistingNewEmployeeDetails(savedEmployee, password);
            javaMailSender.send(message);
        } catch (Exception e){
            return "Employee was registered with employee ID "+savedEmployee.getEmpId()+".";
        }


        return "Employee was registered with employee ID "+savedEmployee.getEmpId()+".";
    }

    /**
     * In this function the employee Designation is created as per the employee Department and
     * Employment level.
     * @param department
     * @param level
     * @return The Designation of employee is returned in String format.
     */
    private String defineDesignation(Department department, EmploymentLevel level) {
        String designation = "";
        if(department == Department.SALES){
            if (level == EmploymentLevel.TRAINEE){
                designation = "Trainee Sales Executive";
            } else if (level == EmploymentLevel.ASSOCIATE){
                designation = "Associate Sale Executive";
            } else if (level == EmploymentLevel.SENIOR){
                designation = "Senior Sale Executive";
            } else if (level == EmploymentLevel.MANAGER){
                designation = "Sales Manager";
            }
        }
        else if(department == Department.MARKETING){
            if (level == EmploymentLevel.TRAINEE){
                designation = "Trainee Marketing Executive";
            } else if (level == EmploymentLevel.ASSOCIATE){
                designation = "Associate Marketing Executive";
            } else if (level == EmploymentLevel.SENIOR){
                designation = "Senior Marketing Executive";
            } else if (level == EmploymentLevel.MANAGER){
                designation = "Marketing Head";
            }
        }
        else if(department == Department.ENGINEERING){
            if (level == EmploymentLevel.TRAINEE){
                designation = "Trainee Software Engineer";
            } else if (level == EmploymentLevel.ASSOCIATE){
                designation = "Associate Software Engineer";
            } else if (level == EmploymentLevel.SENIOR){
                designation = "Senior Software Engineer";
            } else if (level == EmploymentLevel.MANAGER){
                designation = "Project Manager";
            }
        }
        else if(department == Department.FINANCE){
            if (level == EmploymentLevel.TRAINEE){
                designation = "Trainee Financial Analyst";
            } else if (level == EmploymentLevel.ASSOCIATE){
                designation = "Associate Financial Analyst";
            } else if (level == EmploymentLevel.SENIOR){
                designation = "Senior Financial Analyst";
            } else if (level == EmploymentLevel.MANAGER){
                designation = "Financial Analyst Manager";
            }
        }
        else if(department == Department.HR){
            if (level == EmploymentLevel.TRAINEE){
                designation = "Staffing Coordinator";
            } else if (level == EmploymentLevel.ASSOCIATE){
                designation = "HR Associate";
            } else if (level == EmploymentLevel.SENIOR){
                designation = "Senior HR Manager";
            } else if (level == EmploymentLevel.MANAGER){
                designation = "Employee Relations Manager";
            }
        }
        return designation;
    }

    /**
     * In this function admin can update the department of the employee.
     * In case the employee is Transferred from one Department to another.
     * Employement level and salary may also change.
     * @param empId
     * @param newDepartment
     * @return String is returned "Employee's department and salary details were changed."
     */
    @Override
    public String updateDepartment(int empId, Department newDepartment) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        // validate new department input
        if(!isDepartmentValid(newDepartment)){
            throw new InvalidDepartmentException("Department does not exist.");
        }
        // update employee department
        Employee employee = employeeOptional.get();
        employee.setDepartment(newDepartment);
        //Updating Designation of employee
        String newDesignation = defineDesignation(employee.getDepartment(),employee.getEmploymentLevel());
        employee.setDesignation(newDesignation);
        Employee savedEmployee = employeeRepository.save(employee);
        // change salary structure of the employee based on new department
        float baseMonthlySalary = SalaryCalculator.getBaseMonthlySalaryBasedOnDepartmentAndRole(savedEmployee.getDepartment(), savedEmployee.getEmploymentLevel());
        Salary salary = employee.getSalary();
        // update salary details according to new base pay
        SalaryCalculator.refactorSalary(salary, baseMonthlySalary);
        // save salary to save all changes
        salaryRepository.save(salary);

        return "Employee's department and salary details were changed.";
    }

    private boolean isDepartmentValid(Department newDepartment) {
        for(Department department : Department.values()){
            if(department.equals(newDepartment)) return true;
        }
        return false;
    }

    /**
     *  In this function employee level can be promoted and these level are fixed using
     *  EmploymentLevel enum.
     * @param empId
     * @param newLevel
     * @return String is returned if functions works "Employee's employment level and salary details were changed."
     */
    @Override
    public String updateEmploymentLevel(int empId, EmploymentLevel newLevel) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        // validate new level input
        if(!isLevelValid(newLevel)){
            throw new InvalidEmploymentLevelException("Employment level does not exist.");
        }
        // update employee level
        Employee employee = employeeOptional.get();
        employee.setEmploymentLevel(newLevel);
        //Updating Designation of employee
        String newDesignation = defineDesignation(employee.getDepartment(),employee.getEmploymentLevel());
        employee.setDesignation(newDesignation);
        Employee savedEmployee = employeeRepository.save(employee);
        // change salary structure of the employee based on new department
        float baseMonthlySalary = SalaryCalculator.getBaseMonthlySalaryBasedOnDepartmentAndRole(savedEmployee.getDepartment(), savedEmployee.getEmploymentLevel());
        Salary salary = employee.getSalary();
        // update salary details according to new base pay
        SalaryCalculator.refactorSalary(salary, baseMonthlySalary);
        // save salary to save all changes
        salaryRepository.save(salary);

        return "Employee's employment level and salary details were changed.";
    }

    /**
     * In this function the user input provided as employee level is checked with
     * available employee level in EmploymentLevel enum.
     * @param newLevel
     * @return returns a boolean value.
     */

    private boolean isLevelValid(EmploymentLevel newLevel) {
        for(EmploymentLevel level : EmploymentLevel.values()){
            if(level.equals(newLevel)) return true;
        }
        return false;
    }

    /**
     * In this function Admin can update the Employee Access status to GRANTED or RESTRICTED.
     * If account status is Restricted then Employee will not be able to Login into account
     * @param empId
     * @return String is returned based on access type.
     */
    @Override
    public String updateEmployeeAccess(int empId) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        Employee employee = employeeOptional.get();
        // change employee's authorization status
        if(employee.getAccess().equals(AccountAccess.GRANTED)){
            employee.setAccess(AccountAccess.RESTRICTED);
            Employee saveEmployee = employeeRepository.save(employee);
            return "Account access was RESTRICTED to employee with ID "+saveEmployee.getEmpId()+".";
        } else {
            employee.setAccess(AccountAccess.GRANTED);
            Employee saveEmployee = employeeRepository.save(employee);
            return "Account access was GRANTED to employee with ID "+saveEmployee.getEmpId()+".";
        }
    }

    /**
     * In this function records can be viewed of an employee's Unpaid leaves.
     * @param empId
     * @param leaves
     * @return returns a String.
     */
    @Override
    public String recordUnpaidLeave(int empId, int leaves) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        Employee employee = employeeOptional.get();
        // record leave in employees account
        employee.setUnpaidLeaves(employee.getUnpaidLeaves() + leaves);
        employeeRepository.save(employee);

        return leaves+" Leave(s) was recorded in account of employee with ID "+employee.getEmpId()+".";
    }

    /**
     *  In this function due salary of employee can be viewed by admin.
     *
     * @param empId
     * @return Object of DueSalaryDetails is Returned by Converter Class method.
     */
    @Override
    public DueSalaryDetails seeDueSalaryOfAnEmployee(int empId) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        // get employee and corresponding salary to extract details
        Employee employee = employeeOptional.get();
        Salary salary = employee.getSalary();

        // prepare due salary details to send as response
        return Converter.prepareDueSalaryDetails(employee, salary);
    }

    /**
     * In this function previous records of Employee can be viewed and
     * previous due salary can be calculated.
     * @param empId
     * @param amount
     * @return A String object is returned.
     */
    @Override
    public String recordPreviousDueOfAnEmployee(int empId, float amount) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        // check if amount is valid
        if(amount <= 0){
            throw new InvalidInputException("Invalid Amount");
        }
        // record the due payment
        Employee employee = employeeOptional.get();
        employee.setPrevDue(employee.getPrevDue() + amount);
        employeeRepository.save(employee);

        return "A due payment of "+amount+" was recorded in the account of employee with ID "+empId+".";
    }

    /**
     * In this function Employee is paid with his/her salary and payslip is generated.
     * In Converter class preparePaySlip method creates the payslip.
     * @param empId
     * @return Object of PaySlipReplica DTO class is returned.
     */
    @Override
    public PaySlipReplica paySalaryAndGeneratePaySlipOfAnEmployee(int empId) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        Employee employee = employeeOptional.get();
        PaySlip paySlip = Converter.preparePaySlip(employee);
        // save payslip
        PaySlip savedPaySlip = paySlipRepository.save(paySlip);
        // add payslip to employee
        employee.getPayslips().add(paySlip);
        // reset values
        employee.setPrevDue(0);
        employee.setUnpaidLeaves(0);
        // save employee
        employeeRepository.save(employee);
        // prepare a replica of payslip to send to the user
        return Converter.preparePaySlipReplica(savedPaySlip);
    }

    /**
     * In this function admin can view the salary structure pf an employee.
     * Converter class prepareSalaryReplica method is used.
     * @param empId
     * @return SalaryReplica DTO class object is returned.
     */
    @Override
    public SalaryReplica seeSalaryStructureOfAnEmployee(int empId) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        Employee employee = employeeOptional.get();
        // prepare a salary replica to send to the user
        return Converter.prepareSalaryReplica(employee);
    }

    /**
     * In this function admin can view overall due salaries of each and every employee.
     * @return AllDueSalaries DTO class object is returned is this method.
     */
    @Override
    public AllDueSalaries seeDueSalaries() {
        // get a list of all employees
        List<Employee> employeeList = employeeRepository.findAll();
        if(employeeList.isEmpty()){
            throw new EmployeeNotFoundException("Employees database is empty.");
        }
        // iterate over all employees and prepare their Due Salary Details and add it to the response list
        List<DueSalaryDetails> responseList = new ArrayList<>();
        for(Employee employee : employeeList){
            Salary salary = employee.getSalary();
            DueSalaryDetails dueSalaryDetails = Converter.prepareDueSalaryDetails(employee, salary);
            responseList.add(dueSalaryDetails);
        }
        // add response list to the due salaries class and return
        return AllDueSalaries.builder()
                .dueSalaries(responseList)
                .build();
    }

    /**
     * In this function admin can view payment records of particular employees.
     * @param empId
     * @return AllPaymentRecords is a DTO class and its object will be returned.
     */
    @Override
    public AllPaymentRecords seePaymentRecordsOfAnEmployee(int empId) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
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

    /**
     * In this function admin can view payment records of particular employees.
     * @return AllPaymentRecords is a DTO class and its object will be returned.     *
     */
    @Override
    public AllPaymentRecords seePaymentRecords() {
        // get a list of all employees
        List<Employee> employeeList = employeeRepository.findAll();
        if(employeeList.isEmpty()){
            throw new EmployeeNotFoundException("Employees database is empty.");
        }
        // iterate over all employees and prepare replicas of their payment records (payslips) to send to the user
        List<PaySlipReplica> paySlipReplicaList = new ArrayList<>();
        for(Employee employee : employeeList){
            List<PaySlip> paySlipList = employee.getPayslips();
            for(PaySlip paySlip : paySlipList){
                PaySlipReplica paySlipReplica = Converter.preparePaySlipReplica(paySlip);
                paySlipReplicaList.add(paySlipReplica);
            }
        }
        // add payslip replica list to all payment records and return
        return AllPaymentRecords.builder()
                .paymentRecords(paySlipReplicaList)
                .build();
    }

    /**
     * In this function employee details can be deleted by admin.
     * @param empId
     * @return String with message is returned.
     */
    @Override
    public String deleteAnEmployee(int empId) {
        // validate employee
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isEmpty()){
            throw new EmployeeNotFoundException("Employee does not exists.");
        }
        // delete employee and his salary structure and payment records will be deleted automatically
        employeeRepository.deleteById(empId);

        return "Employee with ID "+empId+" was deleted from database.";
    }

    /**
     * This function is used to view Admin account details,
     * Converter class prepareAdminDetails method is called.
     * * @param adminId
     * @return function returns AdminDetails class object.
     */
    @Override
    public AdminDetails seeAccountDetails(int adminId) {
        // validate admin
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if(adminOptional.isEmpty()){
            throw new AdminNotFoundException("Admin does not exists.");
        }
        // update email
        Admin admin = adminOptional.get();
        // prepare Admin Details to send to the user
        return Converter.prepareAdminDetails(admin);
    }
}
