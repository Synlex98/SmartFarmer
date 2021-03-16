package com.fibo.smartfarmer.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    public boolean isUserNameValid(String userName){
        String regex = "^[\\p{L} .'-]+$";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(userName);
        return matcher.matches();
    }

    public boolean isPhoneNumberValid(String phoneNumber){
     String phone=String.valueOf(phoneNumber);
     return
             TextUtils.isDigitsOnly(phone) && phone.length()==10;
    }

    public boolean isPasswordValid(String password){
        return !TextUtils.isEmpty(password) && password.length()>=6 && password.length()<17;
    }

    public boolean isValidEmail(String email) {
        String regex = "^.+@.+\\..+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
