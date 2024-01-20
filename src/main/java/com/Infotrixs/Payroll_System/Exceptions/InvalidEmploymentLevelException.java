package com.Infotrixs.Payroll_System.Exceptions;

public class InvalidEmploymentLevelException extends RuntimeException{
    public InvalidEmploymentLevelException(){
        super();
    }
    public InvalidEmploymentLevelException(String msg){
        super(msg);
    }
}
