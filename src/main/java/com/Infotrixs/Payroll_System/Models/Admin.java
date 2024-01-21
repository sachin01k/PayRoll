package com.Infotrixs.Payroll_System.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "Admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int adminId;

    @Column(nullable = false)
    @Size(min = 4, max = 20)
    String name;

    @Column(nullable = false, unique = true)
    @Email
    String email;

    @Column(nullable = false, unique = true)
    @Size(min = 10, max = 10)
    String phone;

    @Column(nullable = false, unique = true)
    @Size(min = 4, max = 10)
    String username;

    @Column(nullable = false)
    @Size(min = 6)
    String password;

    String passwordResetReqId;

    final String authToken = "AUTHORIZED_ADMIN0542";

    final String role = "ROLE_ADMIN";
}
