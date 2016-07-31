package com.careermate.CORE_LIBS.ACTIVITY;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.KeyEvent;

import com.careermate.CORE_LIBS.HELPER.TinyDB;
import com.careermate.CORE_LIBS.UIMANAGERS.FontManager;
import com.careermate.CORE_LIBS.UIMANAGERS.LogManager;
import com.careermate.CORE_LIBS.UIMANAGERS.ToastManager;
import com.careermate.fbhack.R;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;

/**
 * Created by TuAnh on 4/15/2016.
 */
public abstract class ActivityForm extends Activity {
    public static final String PRESS_BACK_AGAIN_TO_QUIT = ActivityConfig.PRESS_BACK_AGAIN_TO_QUIT;
    public static final int DEFAULT_WAIT_TO_BACK = ActivityConfig.DEFAULT_WAIT_TO_BACK;
    public static final long TIME_CHANGE_ACTIVITY=ActivityConfig.TIME_CHANGE_ACTIVITY;
    public ToastManager T;
    public Context context;
    public TinyDB myDB;
    public LogManager L= LogManager.getINSTANCE();
    public Gson gson = new Gson();
    public FontManager F;
    public AlertDialog progressDialog;
    private Class activity_class;
    private boolean isSetActivity = false;
    //region Back Press
    private int countBackPress = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        T = new ToastManager(context);
        myDB = new TinyDB(context);
        F = new FontManager(context);
        progressDialog = new SpotsDialog(context, R.style.CustomProgressDialog);
        setUpAnimationTransition();
        InitActivity();
        ConnectID();
        SetOnClick();
        DesignLayout();
        MainWork();
    }
    private void setUpAnimationTransition(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide= new Slide();
            slide.setDuration(TIME_CHANGE_ACTIVITY);
            getWindow().setExitTransition(slide);
            Fade fade = new Fade();
            fade.setDuration(TIME_CHANGE_ACTIVITY);
            getWindow().setEnterTransition(fade);
        }
    }
    public void doInitCheckNetWork(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public void closepDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    public void BackPress() {
        if (countBackPress == 0) {
            countBackPress++;
            T.log(PRESS_BACK_AGAIN_TO_QUIT, true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    countBackPress = 0;
                }
            }, DEFAULT_WAIT_TO_BACK);
        } else {
            if (isSetActivity) startActivity(new Intent(this, activity_class));
            finish();
        }
    }

    public void setBackActivity(Class activity_class) {
        this.activity_class = activity_class;
        isSetActivity = true;
    }

    @Override
    public void onBackPressed() {
        BackPress();
        return;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            BackPress();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //endregion
    public abstract void InitActivity();
    public abstract void ConnectID();
    public abstract void SetOnClick();
    public abstract void DesignLayout();
    public abstract void MainWork();
}
