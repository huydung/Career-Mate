package com.careermate.CORE_LIBS.UIMANAGERS;

import com.firebase.client.Firebase;


/**
 * Created by TuAnh on 7/1/2016.
 */
public class FirebaseManager {
    public static FirebaseManager INSTANCE= null;
    public static final String ROOTURL="https://careermate.firebaseio.com/";
    public Firebase root= new Firebase(ROOTURL);
    public static FirebaseManager getINSTANCE(){
        if(INSTANCE==null){
            INSTANCE= new FirebaseManager();
        }
        return INSTANCE;
    }
}
