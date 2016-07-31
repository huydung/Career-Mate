package com.careermate.CORE_LIBS.HELPER;

/**
 * Created by TuAnh on 5/18/2016.
 */
public class StringUtils {
    public static boolean isEmpty(String s){
        if(s==null || s.equals("") || s.equals(null) || s.length()<=0) return true;
        return false;
    }
}
