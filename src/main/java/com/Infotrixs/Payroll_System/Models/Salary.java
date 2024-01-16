package com.Infotrixs.Payroll_System.Models;

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
@Table(name = "Monthly Salaries")
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int salaryId;

    float base;

    float houseRestAllow;

    float convenienceAllow;

    float PF;

    float insurance;

    float inHand;

    // relations
    @OneToOne
    @JoinColumn
    Employee employee;
}
