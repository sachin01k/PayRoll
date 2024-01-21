package com.Infotrixs.Payroll_System.DTOs.Incoming;

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

    String phone;

    String username;

    String password;

    String authToken;
}
