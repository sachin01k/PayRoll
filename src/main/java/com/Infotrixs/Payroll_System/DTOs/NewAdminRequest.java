package com.Infotrixs.Payroll_System.DTOs;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NewAdminRequest {
    String name;
    String email;
    int phone;
    String username;
    String password;
    String authToken;
}
