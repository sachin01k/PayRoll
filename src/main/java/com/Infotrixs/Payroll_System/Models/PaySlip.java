package com.Infotrixs.Payroll_System.Models;

import com.Infotrixs.Payroll_System.Enums.Department;
import com.Infotrixs.Payroll_System.Enums.EmploymentLevel;
import com.Infotrixs.Payroll_System.Enums.PaymentStatus;
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
    int employeeId;

    String employeeName;

    @Enumerated(EnumType.STRING)
    Department department;

    @Enumerated(EnumType.STRING)
    EmploymentLevel employmentLevel;

    String designation;

    // salary details
    float base;

    float houseRestAllow;

    float convenienceAllow;

    float pf;

    float insurance;

    float inHand;

    // payment-related details
    float prevDue;

    float deductionForUnpaidLeaves;

    int unpaidLeaves;

    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;

    // relations
    @ManyToOne
    @JoinColumn(name = "employeeId")
    Employee employee;
}
