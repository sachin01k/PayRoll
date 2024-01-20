package com.Infotrixs.Payroll_System.Utility;

import com.Infotrixs.Payroll_System.Models.Admin;
import com.Infotrixs.Payroll_System.Models.Employee;
import org.springframework.mail.SimpleMailMessage;

public class MailComposer {

    public static SimpleMailMessage composeMailConsistingPasswordResetIdForAdmin(Admin admin){
        String text = "Dear "+admin.getName()+", \n"
                +"\n"
                +"Your request to reset your password is under process. Following is your one time password (OTP). \n"
                +"OTP: "+admin.getPasswordResetReqId()+"\n"
                +"Use this OTP to reset your password.\n"
                +"\n"
                +"Please take appropriate action if the request was not made by you.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(text);
        message.setSubject("Password reset request!!");
        message.setFrom("salikzinerr@gmail.com");
        message.setTo(admin.getEmail());

        return message;
    }

    public static SimpleMailMessage composeMailConsistingPasswordResetIdForEmployee(Employee employee) {
        String text = "Dear "+employee.getName()+", \n"
                +"\n"
                +"Your request to reset your password is under process. Following is your one time password (OTP). \n"
                +"OTP: "+employee.getPasswordResetReqId()+"\n"
                +"Use this OTP to reset your password.\n"
                +"\n"
                +"Please take appropriate action if the request was not made by you.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(text);
        message.setSubject("Password reset request!!");
        message.setFrom("salikzinerr@gmail.com");
        message.setTo(employee.getEmail());

        return message;
    }
}
