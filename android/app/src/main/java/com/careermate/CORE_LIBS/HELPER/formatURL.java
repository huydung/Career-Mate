package com.careermate.CORE_LIBS.HELPER;

import android.util.Pair;

import java.util.List;

/**
 * Created by TuAnh on 2/18/2016.
 */
public class formatURL {
    public static String getParamsUrl(List<Pair<String,String>> list){
        String st="";
        for(int i=0;i<list.size();i++){
            if(i==0)
                st+="?"+list.get(i).first+"="+list.get(i).second;
            else
                st+="&"+list.get(i).first+"="+list.get(i).second;
        }
        return  st;
    }
}
