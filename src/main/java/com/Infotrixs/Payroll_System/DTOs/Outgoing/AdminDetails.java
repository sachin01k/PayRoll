package com.Infotrixs.Payroll_System.DTOs.Outgoing;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AdminDetails {

    int adminId;

    String name;

    String email;

    String phone;

    String username;
}
