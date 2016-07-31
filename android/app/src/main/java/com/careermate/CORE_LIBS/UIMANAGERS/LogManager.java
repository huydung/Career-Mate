package com.careermate.CORE_LIBS.UIMANAGERS;

import android.util.Log;


/**
 * Created by TuAnh on 6/20/2016.
 */
public class LogManager {
    private static LogManager INSTANCE=null;
    public static final boolean isDebug= true;
    public static final String TAG="tag_debug";

    public static LogManager getINSTANCE(){
        if(INSTANCE==null){
            INSTANCE= new LogManager();
        }
        return INSTANCE;
    }
    public void log(String st){
        if(!isDebug) return;
        if(st.equals("")){
            log("null string");return;
        }
        Log.d(TAG,st);
    }
}
