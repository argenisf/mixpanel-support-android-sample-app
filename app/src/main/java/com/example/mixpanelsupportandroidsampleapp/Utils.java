package com.example.mixpanelsupportandroidsampleapp;

import java.util.regex.Pattern;

public class Utils {

    public static Integer getCurrentEpoch(){
        return (int) System.currentTimeMillis();
    }//end of getCurrentEpoch

    public static boolean validateEmail(String email){
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }// end of validateEmail

}// end of Utils class
