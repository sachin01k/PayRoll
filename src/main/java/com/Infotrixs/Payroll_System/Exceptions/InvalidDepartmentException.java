package com.Infotrixs.Payroll_System.Exceptions;

public class InvalidDepartmentException extends RuntimeException{
    public InvalidDepartmentException(){
        super();
    }
    public InvalidDepartmentException(String msg){
        super(msg);
    }
}
