package com.Infotrixs.Payroll_System.Utility;

import java.util.UUID;

public class KeyGenerator {

    public static String generateUniqueKey(){
        StringBuilder str = new StringBuilder();
        for(char ch : String.valueOf(UUID.randomUUID()).toCharArray()){
            if(Character.isDigit(ch)) str.append(ch);
        }
        String s = str.toString();
        return "P-"+s.substring(0, 6);
    }
}
