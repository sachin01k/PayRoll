package com.Infotrixs.Payroll_System.DTOs.Incoming;

import com.Infotrixs.Payroll_System.Enums.Department;
import com.Infotrixs.Payroll_System.Enums.EmploymentLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEmployeeRequest {
    String name;

    String email;

    String phone;

    String username;

    String password;

    Department department;

    EmploymentLevel employmentLevel;

    String bankAccNo;

    String ifsc;
}
