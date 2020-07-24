package com.model;

import java.util.regex.Pattern;

public class Validation {

    public static int usernameValidation(String username){
        if(username.contains(" ")){
            return 0;
        }
        if(username.length()<3 || username.length()>12){
            return 2;
        }
        return 1;
    }

    public static int passwordValidation(String password){
        if(password.contains(" ")){
            return 0;
        }if(password.length()<6 || password.length()>16){
            return 2;
        }
        return 1;
    }

    public static int emailValidation(String email){
        if(email!=null && email.length()!=0) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            Pattern pat = Pattern.compile(emailRegex);
            if (email == null) {
                return 0;
            }
            if (pat.matcher(email).matches()) {
                return 1;
            }
            return 0;
        }
        return 1;
    }

    public static int ageValidation(String strAge){
        int age=0;
        if(strAge!=null && strAge.length()!=0) {
            try {
                age = Integer.parseInt(strAge);
                if(age<=0){
                    return 0;
                }
                if(age>0 && age<10){
                    return 2;
                }
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 1;
    }
}
