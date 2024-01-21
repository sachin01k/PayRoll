package com.Infotrixs.Payroll_System.Exceptions;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(){
        super();
    }
    public EmployeeNotFoundException(String msg){
        super(msg);
    }
}
