package com.Infotrixs.Payroll_System.Exceptions;

public class AccountAccessRestrictedException extends RuntimeException{
    public AccountAccessRestrictedException(){
        super();
    }
    public AccountAccessRestrictedException(String msg){
        super(msg);
    }
}
