package com.Infotrixs.Payroll_System.DTOs.Outgoing;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SalaryReplica {
    String employeeName;

    int salaryId;

    float base;

    float houseRentAllow;

    float convenienceAllow;

    float PF;

    float insurance;

    float inHand;
}
