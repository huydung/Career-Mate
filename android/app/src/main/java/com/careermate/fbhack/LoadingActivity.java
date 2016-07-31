package com.careermate.fbhack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.careermate.CORE_LIBS.ACTIVITY.ActivityForm;
import com.careermate.CORE_LIBS.UIMANAGERS.FirebaseManager;
import com.careermate.KEYS.KFB;
import com.careermate.KEYS.V;
import com.careermate.MODELS.CareerObject;
import com.careermate.MODELS.CourseSeverSideObject;
import com.careermate.MODELS.SkillObject;
import com.careermate.MODELS.SkillServerSideObject;
import com.careermate.MODELS.UserObject;
import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.ExplodeAnimation;
import com.easyandroidanimations.library.ScaleInAnimation;
import com.facebook.AccessToken;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class LoadingActivity extends ActivityForm {
    private ImageView ivLogo;
    private FirebaseManager fb= FirebaseManager.getINSTANCE();
    private int cGood=0,cDone=0;
    private int NUMS_LOAD=4;
    private int NUMS_NOT_LOGIN=4;

    private void update(int x,int y){
        cDone+=x;cGood+=y;
        if(cDone==NUMS_LOAD){
            if(cGood==cDone){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(cDone==NUMS_NOT_LOGIN){
                            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                        }else{
                            startActivity(new Intent(LoadingActivity.this, ProfileActivity.class));
                        }
                        finish();
                    }
                }, 5000);
            }else{
                T.show("Can't connect to server!");
            }
        }
    }
    private void doInitCareerList(){
        Firebase fbCareerList= fb.root.child(KFB.CAREERS);
        fbCareerList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dss) {
                try{
                    L.log("careers :" + dss.getChildrenCount());
                    V.LIST_ALL_CAREER.clear();
                    for (DataSnapshot ds:dss.getChildren()){
                        V.LIST_ALL_CAREER.add(ds.getValue(CareerObject.class));
                    }
                    update(1,1);
                }catch (Exception e){
                    L.log(e.getMessage());
                    update(1,0);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                update(1,0);
            }
        });
    }
    private void doInitSkillList(){
        Firebase fbSkillList= fb.root.child(KFB.SKILLS);
        fbSkillList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dss) {
                try{
                    L.log("skills :" + dss.getChildrenCount());
                    V.LIST_ALL_SKILL.clear();
                    for (DataSnapshot ds:dss.getChildren()){
                        SkillServerSideObject obj= ds.getValue(SkillServerSideObject.class);
                        SkillObject realObj= new SkillObject(ds.getKey(),obj.getName(),obj.getIcon());
                        V.LIST_ALL_SKILL.add(realObj);
                    }
                    update(1,1);
                }catch (Exception e){
                    L.log(e.getMessage());
                    update(1,0);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                update(1,0);
            }
        });
    }
    private void doInitCoursesList(){
        Firebase fbCoursesList= fb.root.child(KFB.COURSES);
        fbCoursesList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dss) {
                try{
                    L.log("courses :" + dss.getChildrenCount());
                    V.LIST_ALL_COURSES.clear();
                    int i=0;
                    for (DataSnapshot ds:dss.getChildren()){
                        L.log("course: "+i +" value: "+ds.toString());

                        CourseSeverSideObject obj= ds.getValue(CourseSeverSideObject.class);
                        i++;
                        V.LIST_ALL_COURSES.add(obj);
                    }
                    update(1,1);
                }catch (Exception e){
                    L.log(e.getMessage());
                    update(1,0);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                update(1,0);
            }
        });
    }
    private void doReadUserInformation(){
        String gsonUser=myDB.getString(V.SAVE_USER);
        V.USER=gson.fromJson(gsonUser, UserObject.class);
        String fbKeyUser=V.MM.get(V.USER.getFbId());
        ///////////////
        T.log("firebase key: "+fbKeyUser);
        Firebase fbUser= FirebaseManager.getINSTANCE().root.child(KFB.USERS).child(fbKeyUser);
        fbUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                try {
                    L.log("users value: "+ds.toString());
                    //V.USER = ds.getValue(UserObject.class);
                    V.USER.setFbId(ds.child("fbId").getValue().toString());
                    V.USER.setUsername(ds.child("username").toString());
                    update(1,1);
                } catch (Exception ee) {
                    T.log("error in users");
                    T.log(ee.getMessage());
                    update(1,0);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                update(1,0);
            }
        });
    }
    private void doReadAllFbIds(){
        Firebase fbFbIds= FirebaseManager.getINSTANCE().root.child(KFB.FBIDS);
        fbFbIds.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dss) {
                try{
                    L.log("number users: " + dss.getChildrenCount());
                    for(DataSnapshot ds:dss.getChildren()){
                        V.MM.put(ds.getKey(), ds.getValue().toString());
                    }
                    if (AccessToken.getCurrentAccessToken() != null || myDB.getString(V.SAVE_USER)!="") {
                        NUMS_LOAD++;
                        doReadUserInformation();
                    }
                    update(1,1);
                }catch (Exception e){
                    update(1,0);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
               update(1,0);
            }
        });
    }
    //region Animation for LOGO
    private void runExplodeAnimation() {
        new ExplodeAnimation(ivLogo)
                .setDuration(1000)
                .setExplodeMatrix(33)
                .setListener(new AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        runScaleInAnimation();
                    }
                })
                .animate();
    }

    private void runScaleInAnimation() {

        new ScaleInAnimation(ivLogo)
                .setDuration(1000)
                .setListener(new AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        AnimationForLogo();
                    }
                })
                .animate();
    }

    private void AnimationForLogo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runExplodeAnimation();
                    }
                });
            }
        }, 3000);
    }
    //endregion


    //region CORE INIT
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.ac_loading);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void InitActivity() {

    }

    @Override
    public void ConnectID() {
        ivLogo= (ImageView) findViewById(R.id.ivLogo);
    }

    @Override
    public void SetOnClick() {

    }

    @Override
    public void DesignLayout() {

    }

    @Override
    public void MainWork() {
        AnimationForLogo();
        doReadAllFbIds();
        doInitCareerList();
        doInitSkillList();
        doInitCoursesList();

    }
    //endregion
}
