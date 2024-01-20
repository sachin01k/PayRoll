package com.Infotrixs.Payroll_System.Exceptions;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException(){
        super();
    }
    public AdminNotFoundException(String msg){
        super(msg);
    }
}
