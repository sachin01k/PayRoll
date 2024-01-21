package com.Infotrixs.Payroll_System.Exceptions;

public class InvalidOTPException extends RuntimeException{
    public InvalidOTPException(){
        super();
    }
    public InvalidOTPException(String msg){
        super(msg);
    }
}
