package com.careermate.CORE_LIBS.SOCIAL;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.careermate.CORE_LIBS.UIMANAGERS.LogManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;


/**
 * Created by TuAnh on 7/27/2016.
 */
public class FacebookObject {
    private Context context;
    private Activity activity;
    private LogManager L= LogManager.getINSTANCE();

    public FacebookObject(Context context) {
        this.context = context;
        activity= (Activity) context;
    }
    public void getAccountInformation(String... params){//"id,name,email,gender,birthday"
        if(AccessToken.getCurrentAccessToken()==null){
            L.log("you need login to facebook first!");
            ((OnGetInformationFinished)activity).onCancel();
            return;
        }
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        L.log("request completed!");
                        ((OnGetInformationFinished)activity).onSuccess(object,response);

                    }
                });
        String param="";
        if(params.length>0){
            for(int i=0;i<params.length-1;i++) param+=params[i]+",";
            param+=params[params.length-1];
        }
        Bundle parameters = new Bundle();
        parameters.putString("fields",param);
        request.setParameters(parameters);
        request.executeAsync();
    }
    /*
    * need declare  callbackManager=CallbackManager.Factory.create();
    * then in
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    * */
    public void Login(CallbackManager callbackManager){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                L.log("success login to facebook :"+ loginResult.toString());
                ((OnLoginFinished) activity).onSuccess(loginResult);
            }

            @Override
            public void onCancel() {
                L.log("cancel login to facebook");
                ((OnLoginFinished) activity).onCancel();
            }

            @Override
            public void onError(FacebookException error) {
                L.log("error login to facebook "+error.getMessage());
                ((OnLoginFinished) activity).onError(error);
            }
        });

    }
    public interface OnLoginFinished{
        void onSuccess(LoginResult loginResult);
        void onCancel();
        void onError(FacebookException error);
    }
    public interface OnGetInformationFinished{
        void onSuccess(JSONObject object, GraphResponse response);
        void onCancel();
    }
}
