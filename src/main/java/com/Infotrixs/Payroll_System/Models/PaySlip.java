package com.Infotrixs.Payroll_System.Models;

import com.Infotrixs.Payroll_System.Enums.Department;
import com.Infotrixs.Payroll_System.Enums.EmploymentLevel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "Payslips")
public class PaySlip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int paySlipId;

    // employee details
    @Column(insertable = false, updatable = false)
    int empId;

    String empName;

    @Enumerated(EnumType.STRING)
    Department department;

    @Enumerated(EnumType.STRING)
    EmploymentLevel employmentLevel;

    String designation;

    // salary details
    float base;

    float houseRestAllow;

    float convenienceAllow;

    float PF;

    float insurance;

    float inHand;

    // payment-related details
    int prevDue;

    int unpaidLeaves;

    boolean paid = false;

    // relations
    @ManyToOne
    @JoinColumn(name = "empId")
    Employee employee;
}
