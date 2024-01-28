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

    /**
     * In this function an authorized user request to create new Admin account.
     * The request object is Forwarded to AdminService class addNewAdmin method.
     *
     * @param request
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @PostMapping("/add-new-admin")
    public ResponseEntity addNewAdmin(@RequestBody NewAdminRequest request){
        try{
            String response = adminService.addNewAdmin(request);
            return new ResponseEntity (response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Here Admin can request to update his/her email-id.
     * The request will be processed in the updateEmail method of AdminService class.     *
     * @param adminId
     * @param newEmail
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @PutMapping("/update-email/admin-id/{adminId}/new-email/{newEmail}")
    public ResponseEntity updateEmail(@PathVariable("adminId") int adminId, @PathVariable("newEmail") String newEmail){
        try{
            String response = adminService.updateEmail(adminId, newEmail);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * This Function allows Admins request to change existing Mobile Number in database.
     * The request will be forwarded to updatePhone method of Admin Service Class.
     * @param adminId
     * @param newPhone
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @PutMapping("/update-phone/admin-id/{adminId}/new-phone/{newPhone}")
    public ResponseEntity updatePhone(@PathVariable("adminId") int adminId, @PathVariable("newPhone") String newPhone){
        try{
            String response = adminService.updatePhone(adminId, newPhone);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * The request by admin to change his/her username is controlled by the below Function.
     * The request is forwarded to updateUsername method of AdminService.
     * @param adminId
     * @param newUsername
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @PutMapping("/update-username/admin-id/{adminId}/new-username/{newUsername}")
    public ResponseEntity updateUsername(@PathVariable("adminId") int adminId, @PathVariable("newUsername") String newUsername){
        try{
            String response = adminService.updateUsername(adminId, newUsername);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * This is a Controller function to control the request to reset password by Admin.
     * The request is forwarded to passwordResetRequest method.
     * An Otp is will be mailed to verify the admin.
     * @param adminId
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @GetMapping("/password-reset-request/admin-id/{adminId}")
    public ResponseEntity passwordResetRequest(@PathVariable("adminId") int adminId){
        try{
            String response = adminService.passwordResetRequest(adminId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * After verifying the OTP the further reset request will be processed by this following method.
     * AdminService Class resetPassword method will do the implementation.
     * @param adminId
     * @param otp
     * @param newPassword
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @PutMapping("/reset-password/admin-id/{adminId}/otp/{otp}/new-password/{newPassword}")
    public ResponseEntity resetPassword(@PathVariable("adminId") int adminId, @PathVariable("otp") String otp, @PathVariable("newPassword") String newPassword){
        try{
            String response = adminService.resetPassword(adminId, otp, newPassword);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Admin can add new Employee to the Database.
     * Admin request will be processed by addNewEmployee method of AdminServiceImpl Class.
     * @param request
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @PostMapping("/add-new-employee")
    public ResponseEntity addNewEmployee(@RequestBody NewEmployeeRequest request){
        try{
            String response = adminService.addNewEmployee(request);
            return new ResponseEntity (response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Admin can Update the Employee Department if in future employee is Shifted to new Department.
     * @param empId
     * @param newDepartment
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @PutMapping("/update-department/employee-id/{employeeId}/new-department/{newDepartment}")
    public ResponseEntity updateDepartment(@PathVariable("employeeId") int empId, @PathVariable("newDepartment") Department newDepartment){
        try{
            String response = adminService.updateDepartment(empId, newDepartment);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Admin can Update the Employee Level if in future employee is promoted to new Position.
     * @param empId
     * @param newLevel
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @PutMapping("/update-employment-level/employee-id/{employeeId}/new-level/{newLevel}")
    public ResponseEntity updateEmploymentLevel(@PathVariable("employeeId") int empId, @PathVariable("newLevel") EmploymentLevel newLevel){
        try{
            String response = adminService.updateEmploymentLevel(empId, newLevel);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Employee Access can be managed by Admin ex. Admin can restrict employee from access acount.
     * The implementation is provided in AdminService class.
     * @param empId
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @PutMapping("/change-employee-access/employee-id/{employeeId}")
    public ResponseEntity changeEmployeeAccess(@PathVariable("employeeId") int empId){
        try{
            String response = adminService.updateEmployeeAccess(empId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Admin can record the unpaid leaves of an employee in current working month.
     * @param empId
     * @param leaves
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @PutMapping("/record-unpaid-leave/employee-id/{employeeId}/leaves/{leaves}")
    public ResponseEntity recordUnpaidLeave(@PathVariable("employeeId") int empId, @PathVariable("leaves") int leaves){
        try{
            String response = adminService.recordUnpaidLeave(empId, leaves);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Admin can check for any salary due of an employee to be paid
     * so can be including in current month payment.
     * @param empId
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @GetMapping("/see-due-salary-of-an-employee/employee-id/{employeeId}")
    public ResponseEntity seeDueSalaryOfAnEmployee(@PathVariable("employeeId") int empId){
        try{
            DueSalaryDetails response = adminService.seeDueSalaryOfAnEmployee(empId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Previous working months dues records of an employee can be accessed by Admin.
     * @param empId
     * @param amount
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @PutMapping("/record-previous-due-of-an-employee/employee-id/{employeeId}/amount/{amount}")
    public ResponseEntity recordPreviousDueOfAnEmployee(@PathVariable("employeeId") int empId, @PathVariable("amount") float amount){
        try{
            String response = adminService.recordPreviousDueOfAnEmployee(empId, amount);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Admin will pay the salary and generate the salary slip for employee.
     * @param empId
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @PostMapping("/pay-salary-and-generate-pay-slip-of-an-employee/employee-id/{employeeId}")
    public ResponseEntity paySalaryAndGeneratePaySlipOfAnEmployee(@PathVariable("employeeId") int empId){
        try{
            PaySlipReplica response = adminService.paySalaryAndGeneratePaySlipOfAnEmployee(empId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Admin can See the Salary structure of an employee according to department and employment level.
     * @param empId
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @GetMapping("/see-salary-structure-of-an-employee/employee-id/{employeeId}")
    public ResponseEntity seeSalaryStructureOfAnEmployee(@PathVariable("employeeId") int empId){
        try{
            SalaryReplica response = adminService.seeSalaryStructureOfAnEmployee(empId);
            return new ResponseEntity (response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity (e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * If there are any dues in salaries can be viewed using
     * below controller.
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @GetMapping("/see-due-salaries")
    public ResponseEntity seeDueSalaries(){
        try{
            AllDueSalaries response = adminService.seeDueSalaries();
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Admin can view previous salary payment records
     * of a particular employee.
     * @param empId
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @GetMapping("/see-payment-records-of-an-employee/employee-id/{employeeId}")
    public ResponseEntity seePaymentRecordsOfAnEmployee(@PathVariable("employeeId") int empId){
        try{
            AllPaymentRecords response = adminService.seePaymentRecordsOfAnEmployee(empId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Admin can view overall payment records of all employees.
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @GetMapping("/see-payment-records")
    public ResponseEntity seePaymentRecords(){
        try{
            AllPaymentRecords response = adminService.seePaymentRecords();
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Admin can delete the Employee Account from database if required to do so.
     * @param empId
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
    @DeleteMapping("/delete-an-employee/employee-id/{employeeId}")
    public ResponseEntity deleteAnEmployee(@PathVariable("employeeId") int empId){
        try{
            String response = adminService.deleteAnEmployee(empId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Admin can view Employee Account Details.
     * @param adminId
     * @return ResponseEntity Object will be returned which holds message and status code.
     */
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
