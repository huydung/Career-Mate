package com.careermate.CORE_LIBS.UIMANAGERS;

/**
 * Created by TuAnh on 5/15/2016.
 */
public class TimeManager {
    public static String toString(long i){
        String st="";
        int sec= (int) (i/1000);
        int minute= sec/60;
        int hour= minute/60;
        int day= hour/24;
        if(day>0) st+=String.valueOf(day)+" days ";
        if(hour%24>0)st+=String.valueOf(hour%24)+" hours ";
        if(day==0 && minute%60>0)st+=String.valueOf(minute%60)+" minutes ";
        if(day==0 && sec%60>0) st+=String.valueOf(sec%60)+" seconds ";
        st+="ago";
        return st;
    }
}
