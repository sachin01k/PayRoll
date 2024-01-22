package com.Infotrixs.Payroll_System.DTOs.Outgoing;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class EmployeeDetails {

    int empId;

    String name;

    String email;

    String phone;

    String username;

    String department;

    String employmentLevel;

    String designation;

    String accountAccess;

    String bankAccNo;

    String ifsc;


}
