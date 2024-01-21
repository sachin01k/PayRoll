package com.Infotrixs.Payroll_System.DTOs.Outgoing;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PaySlipReplica {
    int paySlipId;

    int employeeId;

    String employeeName;

    String department;

    String employmentLevel;

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

    String paymentStatus;
}
