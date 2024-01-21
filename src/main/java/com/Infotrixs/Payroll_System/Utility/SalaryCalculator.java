package com.Infotrixs.Payroll_System.Utility;

import com.Infotrixs.Payroll_System.Enums.Department;
import com.Infotrixs.Payroll_System.Enums.EmploymentLevel;
import com.Infotrixs.Payroll_System.Models.Employee;
import com.Infotrixs.Payroll_System.Models.Salary;

public class SalaryCalculator {
    final static int HRA_Rate = 9;
    final static int conAllowanceRate = 5;
    final static int pfRate = 18;
    final static float insurance = 650.0F;
    public static Salary generateNewMonthlySalary(Employee employee, float baseMonthlySalary){
        // Calculate salary components
        float HRA = (HRA_Rate * baseMonthlySalary) / 100;
        float conAllowance = (conAllowanceRate * baseMonthlySalary) / 100;
        float PF = (pfRate * baseMonthlySalary) / 100;

        float earnings = baseMonthlySalary + HRA + conAllowance;
        float deductions = PF + insurance;

        float inHandMonthlySalary = earnings - deductions;

        // create salary
        return Salary.builder()
                .base(baseMonthlySalary)
                .houseRentAllow(HRA)
                .convenienceAllow(conAllowance)
                .PF(PF)
                .insurance(insurance)
                .inHand(inHandMonthlySalary)
                .employee(employee)
                .build();
    }

    public static float getBaseMonthlySalaryBasedOnDepartmentAndRole(Department department, EmploymentLevel level) {
        // There are five departments, each department has four types (levels) of roles. Fix salary based on department and role.
        float baseMonthlySalary = 0.0F;
        if(department == Department.SALES){
            if (level == EmploymentLevel.TRAINEE){
                baseMonthlySalary = 18000;
            } else if (level == EmploymentLevel.ASSOCIATE){
                baseMonthlySalary = 25000;
            } else if (level == EmploymentLevel.SENIOR){
                baseMonthlySalary = 40000;
            } else if (level == EmploymentLevel.MANAGER){
                baseMonthlySalary = 65000;
            }
        }
        else if(department == Department.MARKETING){
            if (level == EmploymentLevel.TRAINEE){
                baseMonthlySalary = 18000;
            } else if (level == EmploymentLevel.ASSOCIATE){
                baseMonthlySalary = 25000;
            } else if (level == EmploymentLevel.SENIOR){
                baseMonthlySalary = 50000;
            } else if (level == EmploymentLevel.MANAGER){
                baseMonthlySalary = 70000;
            }
        }
        else if(department == Department.ENGINEERING){
            if (level == EmploymentLevel.TRAINEE){
                baseMonthlySalary = 25000;
            } else if (level == EmploymentLevel.ASSOCIATE){
                baseMonthlySalary = 32000;
            } else if (level == EmploymentLevel.SENIOR){
                baseMonthlySalary = 65000;
            } else if (level == EmploymentLevel.MANAGER){
                baseMonthlySalary = 100000;
            }
        }
        else if(department == Department.FINANCE){
            if (level == EmploymentLevel.TRAINEE){
                baseMonthlySalary = 15000;
            } else if (level == EmploymentLevel.ASSOCIATE){
                baseMonthlySalary = 20000;
            } else if (level == EmploymentLevel.SENIOR){
                baseMonthlySalary = 35000;
            } else if (level == EmploymentLevel.MANAGER){
                baseMonthlySalary = 50000;
            }
        }
        else if(department == Department.HR){
            if (level == EmploymentLevel.TRAINEE){
                baseMonthlySalary = 15000;
            } else if (level == EmploymentLevel.ASSOCIATE){
                baseMonthlySalary = 20000;
            } else if (level == EmploymentLevel.SENIOR){
                baseMonthlySalary = 32000;
            } else if (level == EmploymentLevel.MANAGER){
                baseMonthlySalary = 45000;
            }
        }

        return baseMonthlySalary;
    }

    public static void refactorSalary(Salary salary, float baseMonthlySalary) {
        // Calculate salary components
        float HRA = (HRA_Rate * baseMonthlySalary) / 100;
        float conAllowance = (conAllowanceRate * baseMonthlySalary) / 100;
        float PF = (pfRate * baseMonthlySalary) / 100;

        float earnings = baseMonthlySalary + HRA + conAllowance;
        float deductions = PF + insurance;

        float inHandMonthlySalary = earnings - deductions;

        // update salary components
        salary.setBase(baseMonthlySalary);
        salary.setHouseRentAllow(HRA);
        salary.setConvenienceAllow(conAllowance);
        salary.setPF(PF);
        salary.setInsurance(insurance);
        salary.setInHand(inHandMonthlySalary);
    }
}
