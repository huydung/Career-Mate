package com.careermate.CORE_LIBS.SOCIAL;

import android.os.Bundle;
import android.util.Log;

import com.careermate.CORE_LIBS.UIMANAGERS.LogManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by TuAnh on 7/27/2016.
 */
public class FacebookManager {
    public static LogManager L= LogManager.getINSTANCE();
    public static void getAccountInformation(){
        if(AccessToken.getCurrentAccessToken()==null){
            L.log("chua dang nhap");
            return;
        }
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());
                        try {
                            String id = object.getString("id");
                            //String birthday = object.getString("birthday");
                            L.log("email:"+id);
                            L.log("https://graph.facebook.com/"+object.getString("id")+"/picture?type=large");
                            //L.log("birthday:"+birthday);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            L.log(e.getMessage());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
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
    public static void Login(CallbackManager callbackManager){
        L.log("go here!");
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                L.log("success login to facebook ");
                getAccountInformation();
            }

            @Override
            public void onCancel() {
                L.log("cancel login to facebook");
            }

            @Override
            public void onError(FacebookException error) {
                L.log("error when login to facebook");
                L.log(error.getMessage());
            }
        });

    }
}
