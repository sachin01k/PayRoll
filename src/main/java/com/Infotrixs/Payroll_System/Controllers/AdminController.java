package com.Infotrixs.Payroll_System.Controllers;

import com.Infotrixs.Payroll_System.DTOs.Incoming.NewAdminRequest;
import com.Infotrixs.Payroll_System.DTOs.Incoming.NewEmployeeRequest;
import com.Infotrixs.Payroll_System.DTOs.Outgoing.*;
import com.Infotrixs.Payroll_System.Enums.Department;
import com.Infotrixs.Payroll_System.Enums.EmploymentLevel;
import com.Infotrixs.Payroll_System.Services.Impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    final AdminServiceImpl adminService;
    @Autowired
    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }
    @PostMapping("/add-new-admin")
    public ResponseEntity addNewAdmin(@RequestBody NewAdminRequest request){
        try{
            String response = adminService.addNewAdmin(request);
            return new ResponseEntity (response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    @PutMapping("/update-email/admin-id/{adminId}/new-email/{newEmail}")
    public ResponseEntity updateEmail(@PathVariable("adminId") int adminId, @PathVariable("newEmail") String newEmail){
        try{
            String response = adminService.updateEmail(adminId, newEmail);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/update-phone/admin-id/{adminId}/new-phone/{newPhone}")
    public ResponseEntity updatePhone(@PathVariable("adminId") int adminId, @PathVariable("newPhone") String newPhone){
        try{
            String response = adminService.updatePhone(adminId, newPhone);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/update-username/admin-id/{adminId}/new-username/{newUsername}")
    public ResponseEntity updateUsername(@PathVariable("adminId") int adminId, @PathVariable("newUsername") String newUsername){
        try{
            String response = adminService.updateUsername(adminId, newUsername);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/password-reset-request/admin-id/{adminId}")
    public ResponseEntity passwordResetRequest(@PathVariable("adminId") int adminId){
        try{
            String response = adminService.passwordResetRequest(adminId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/reset-password/admin-id/{adminId}/otp/{otp}/new-password/{newPassword}")
    public ResponseEntity resetPassword(@PathVariable("adminId") int adminId, @PathVariable("otp") String otp, @PathVariable("newPassword") String newPassword){
        try{
            String response = adminService.resetPassword(adminId, otp, newPassword);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/add-new-employee")
    public ResponseEntity addNewEmployee(@RequestBody NewEmployeeRequest request){
        try{
            String response = adminService.addNewEmployee(request);
            return new ResponseEntity (response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/update-department/employee-id/{employeeId}/new-department/{newDepartment}")
    public ResponseEntity updateDepartment(@PathVariable("employeeId") int empId, @PathVariable("newDepartment") Department newDepartment){
        try{
            String response = adminService.updateDepartment(empId, newDepartment);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/update-employment-level/employee-id/{employeeId}/new-level/{newLevel}")
    public ResponseEntity updateEmploymentLevel(@PathVariable("employeeId") int empId, @PathVariable("newLevel") EmploymentLevel newLevel){
        try{
            String response = adminService.updateEmploymentLevel(empId, newLevel);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/change-employee-access/employee-id/{employeeId}")
    public ResponseEntity changeEmployeeAccess(@PathVariable("employeeId") int empId){
        try{
            String response = adminService.updateEmployeeAccess(empId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/record-unpaid-leave/employee-id/{employeeId}/leaves/{leaves}")
    public ResponseEntity recordUnpaidLeave(@PathVariable("employeeId") int empId, @PathVariable("leaves") int leaves){
        try{
            String response = adminService.recordUnpaidLeave(empId, leaves);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/see-due-salary-of-an-employee/employee-id/{employeeId}")
    public ResponseEntity seeDueSalaryOfAnEmployee(@PathVariable("employeeId") int empId){
        try{
            DueSalaryDetails response = adminService.seeDueSalaryOfAnEmployee(empId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/record-previous-due-of-an-employee/employee-id/{employeeId}/amount/{amount}")
    public ResponseEntity recordPreviousDueOfAnEmployee(@PathVariable("employeeId") int empId, @PathVariable("amount") float amount){
        try{
            String response = adminService.recordPreviousDueOfAnEmployee(empId, amount);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/pay-salary-and-generate-pay-slip-of-an-employee/employee-id/{employeeId}")
    public ResponseEntity paySalaryAndGeneratePaySlipOfAnEmployee(@PathVariable("employeeId") int empId){
        try{
            PaySlipReplica response = adminService.paySalaryAndGeneratePaySlipOfAnEmployee(empId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/see-salary-structure-of-an-employee/employee-id/{employeeId}")
    public ResponseEntity seeSalaryStructureOfAnEmployee(@PathVariable("employeeId") int empId){
        try{
            SalaryReplica response = adminService.seeSalaryStructureOfAnEmployee(empId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/see-due-salaries")
    public ResponseEntity seeDueSalaries(){
        try{
            AllDueSalaries response = adminService.seeDueSalaries();
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/see-payment-records-of-an-employee/employee-id/{employeeId}")
    public ResponseEntity seePaymentRecordsOfAnEmployee(@PathVariable("employeeId") int empId){
        try{
            AllPaymentRecords response = adminService.seePaymentRecordsOfAnEmployee(empId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/see-payment-records")
    public ResponseEntity seePaymentRecords(){
        try{
            AllPaymentRecords response = adminService.seePaymentRecords();
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/delete-an-employee/employee-id/{employeeId}")
    public ResponseEntity deleteAnEmployee(@PathVariable("employeeId") int empId){
        try{
            String response = adminService.deleteAnEmployee(empId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/see-account-details/admin-id/{adminId}")
    public ResponseEntity seeAccountDetails(@PathVariable("adminId") int adminId){
        try{
            AdminDetails response = adminService.seeAccountDetails(adminId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

}
