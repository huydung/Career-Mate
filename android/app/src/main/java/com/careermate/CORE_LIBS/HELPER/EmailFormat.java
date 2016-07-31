package com.careermate.CORE_LIBS.HELPER;

/**
 * Created by TuAnh on 3/14/2016.
 */
public class EmailFormat {
    public static boolean checkEmailFormat(String st) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(st);
        return m.matches();
    }
}
