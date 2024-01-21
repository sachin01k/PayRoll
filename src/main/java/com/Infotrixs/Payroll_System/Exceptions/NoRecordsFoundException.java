package com.Infotrixs.Payroll_System.Exceptions;

public class NoRecordsFoundException extends RuntimeException{
    public NoRecordsFoundException(){
        super();
    }
    public NoRecordsFoundException(String msg){
        super(msg);
    }
}
