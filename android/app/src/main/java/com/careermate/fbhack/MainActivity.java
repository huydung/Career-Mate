package com.careermate.fbhack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.careermate.CORE_LIBS.ACTIVITY.ActivityFormFragment;
import com.careermate.CORE_LIBS.SOCIAL.FacebookObject;
import com.careermate.CORE_LIBS.UIMANAGERS.FirebaseManager;
import com.careermate.KEYS.KFB;
import com.careermate.KEYS.V;
import com.careermate.MODELS.UserObject;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONObject;

public class MainActivity extends ActivityFormFragment implements View.OnClickListener, FacebookObject.OnLoginFinished, FacebookObject.OnGetInformationFinished {
    private LoginButton btnFacebookLogin;
    private FacebookObject fbobj;
    private CallbackManager callbackManager;
    private boolean isAlreadyLogin = false;
    ///////////
    private void doCreateNewUser(){
        T.log("create new user " + V.USER.toString());
        if(!V.MM.containsKey(V.USER.getFbId())){
            Firebase fbUser= FirebaseManager.getINSTANCE().root.child(KFB.USERS).push();
            fbUser.setValue(V.USER);
            FirebaseManager.getINSTANCE().root.child(KFB.FBIDS).child(V.USER.getFbId()).setValue(fbUser.getKey());
        }
        myDB.putString(V.SAVE_USER,gson.toJson(V.USER));
        doProfileActivity();
    }
    private void doLogin() {
        if (!isAlreadyLogin) {
            callbackManager = CallbackManager.Factory.create();
            fbobj.Login(callbackManager);
        }
    }
    private void doProfileActivity(){
        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
        finish();
    }
    private void doNextActivity(){
        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
        finish();
    }
    private void doReadUserInformation(){
        String gsonUser=myDB.getString(V.SAVE_USER);
        V.USER=gson.fromJson(gsonUser, UserObject.class);
        String fbKeyUser=V.MM.get(V.USER.getFbId());
        ///////////////
        Firebase fbUser= FirebaseManager.getINSTANCE().root.child(KFB.USERS).child(fbKeyUser);
        fbUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                try {
                    V.USER = ds.getValue(UserObject.class);
                    doNextActivity();
                } catch (Exception ee) {
                    T.log(ee.getMessage());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                T.show("Error!!");
            }
        });
    }

    //region CORE INIT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.ac_main);
        super.onCreate(savedInstanceState);
    }
    private void realInit(){
        if (AccessToken.getCurrentAccessToken() != null && myDB.getString(V.SAVE_USER)!="") {
            isAlreadyLogin = true;
            doReadUserInformation();
        }
    }
    @Override
    public void InitActivity() {
        //realInit();
        fbobj = new FacebookObject(context);
    }

    @Override
    public void ConnectID() {
        btnFacebookLogin = (LoginButton) findViewById(R.id.btnFacebookLogin);
    }

    @Override
    public void SetOnClick() {
        btnFacebookLogin.setOnClickListener(this);
    }

    @Override
    public void DesignLayout() {

    }

    @Override
    public void MainWork() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFacebookLogin:
                doLogin();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        fbobj.getAccountInformation("id", "name");
    }

    @Override
    public void onSuccess(JSONObject object, GraphResponse response) {
        try {
            L.log("id: " + object.getString("id"));
            L.log("name: " + object.getString("name"));
            V.USER.setFbId(object.getString("id"));
            V.USER.setUsername(object.getString("name"));
            doCreateNewUser();
        } catch (Exception e) {
            L.log(e.getMessage());
        }
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }
    //endregion
}
