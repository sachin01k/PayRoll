package com.Infotrixs.Payroll_System.Exceptions;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(){
        super();
    }
    public InvalidInputException(String msg){
        super(msg);
    }
}
