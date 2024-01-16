package com.Infotrixs.Payroll_System.Exceptions;

public class InvalidAdminAuthTokenException extends RuntimeException{
    public InvalidAdminAuthTokenException(){
        super();
    }
    public InvalidAdminAuthTokenException(String msg){
        super(msg);
    }
}
