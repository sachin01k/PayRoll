package com.Infotrixs.Payroll_System.Models;

import com.Infotrixs.Payroll_System.Enums.Department;
import com.Infotrixs.Payroll_System.Enums.EmploymentLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "Employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int empId;

    @Column(nullable = false)
    @Size(min = 4, max = 20)
    String name;

    @Column(nullable = false, unique = true)
    @Email
    String email;

    @Column(nullable = false, unique = true)
    @Size(min = 10, max = 10)
    int phone;

    @Column(nullable = false, unique = true)
    @Size(min = 4, max = 10)
    String username;

    @Column(nullable = false)
    @Size(min = 6, max = 12)
    String password;

    // employment details
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Department department;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    EmploymentLevel employmentLevel;

    @Column(nullable = false)
    String designation;

    boolean authorized = true;

    final String role = "ROLE_EMPLOYEE";

    // bank details
    @Column(nullable = false, unique = true)
    String bankAccNo;

    @Column(nullable = false)
    String IFSC;

    // payment-related details
    int prevDue = 0;
    int unpaidLeaves = 0;

    // relations
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    Salary salary;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    ArrayList<PaySlip> payslips = new ArrayList<>();
}
