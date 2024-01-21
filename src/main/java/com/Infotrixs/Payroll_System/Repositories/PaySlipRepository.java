package com.Infotrixs.Payroll_System.Repositories;

import com.Infotrixs.Payroll_System.Models.PaySlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaySlipRepository extends JpaRepository<PaySlip, Integer> {
}
