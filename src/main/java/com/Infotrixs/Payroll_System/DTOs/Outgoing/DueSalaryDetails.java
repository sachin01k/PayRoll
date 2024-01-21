package com.Infotrixs.Payroll_System.DTOs.Outgoing;

import com.Infotrixs.Payroll_System.Enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DueSalaryDetails {

    int salaryId;

    int employeeId;

    String employeeName;

    float base;

    float houseRentAllow;

    float convenienceAllow;

    float PF;

    float insurance;

    float inHand;

    float previousDues;

    float deductionForUnpaidLeaves;

    int unpaidLeaves;

    float netPay;

    String paymentStatus;

    String department;

    String employmentLevel;

    String designation;
}
