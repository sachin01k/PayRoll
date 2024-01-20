package com.Infotrixs.Payroll_System.Repositories;

import com.Infotrixs.Payroll_System.Models.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
}
