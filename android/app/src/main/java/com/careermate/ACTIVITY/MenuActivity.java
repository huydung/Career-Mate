package com.careermate.ACTIVITY;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.careermate.CORE_LIBS.ACTIVITY.ActivityConfig;
import com.careermate.CORE_LIBS.HELPER.MyConvert;
import com.careermate.CORE_LIBS.HELPER.TinyDB;
import com.careermate.CORE_LIBS.UIMANAGERS.FontManager;
import com.careermate.CORE_LIBS.UIMANAGERS.LogManager;
import com.careermate.CORE_LIBS.UIMANAGERS.ToastManager;
import com.careermate.KEYS.V;
import com.careermate.fbhack.AboutActivity;
import com.careermate.fbhack.ComunityActivity;
import com.careermate.fbhack.CourseActivity;
import com.careermate.fbhack.HistoryActivity;
import com.careermate.fbhack.ProfileActivity;
import com.careermate.fbhack.R;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import dmax.dialog.SpotsDialog;

/**
 * Created by TuAnh on 4/15/2016.
 */
public abstract class MenuActivity extends SlidingFragmentActivity {
    public static final String PRESS_BACK_AGAIN_TO_QUIT = ActivityConfig.PRESS_BACK_AGAIN_TO_QUIT;
    public static final int DEFAULT_WAIT_TO_BACK = ActivityConfig.DEFAULT_WAIT_TO_BACK;
    public ToastManager T;
    public Context context;
    public TinyDB myDB;
    public LogManager L= LogManager.getINSTANCE();
    public Gson gson = new Gson();
    public FontManager F;
    public AlertDialog progressDialog;
    private Class activity_class;
    private boolean isSetActivity = false;
    /////////////////////////////
    private ImageView ivAvatar;
    private LinearLayout linProfile,linCourse,linLearn,linHistory,linCommunity;
    private TextView tvAbout;


    private void doProfile(){
        startActivity(new Intent(context, ProfileActivity.class));
        finish();
    }
    private void doCourse(){
        startActivity(new Intent(context, CourseActivity.class));
        finish();
    }
    private void doHistory(){
        startActivity(new Intent(context, HistoryActivity.class));
        finish();
    }
    private void goMess1(){
        //good
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/661794463969839"));
        try
        {
            startActivity(intent);
        }
        catch (ActivityNotFoundException ex)
        {
            Toast.makeText(this,
                    "Oups!Can't open Facebook messenger right now. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void doLearn(){
        /*startActivity(new Intent(context, ProfileActivity.class));
        finish();*/
       /* Intent intent= new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Hello");
        intent.setType("text/plain");
        intent.setPackage("com.facebook.orca");*/
        Intent intent= new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("fb://messaging/661794463969839"));
        try
        {
            startActivity(intent);
        }
        catch (ActivityNotFoundException ex)
        {
            Toast.makeText(this,
                    "Oups!Can't open Facebook messenger right now. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void doCommunity(){
        startActivity(new Intent(context, ComunityActivity.class));
        finish();
    }
    private View.OnClickListener menuClick= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ivAvatar:
                case R.id.linProfile: doProfile();break;
                case R.id.linCourses: doCourse();break;
                case R.id.linHistory: doHistory();break;
                case R.id.linLearn: doLearn();break;
                case R.id.linCommunity:doCommunity();break;
                case R.id.tvAbout: startActivity(new Intent(context, AboutActivity.class));finish();break;
                default:L.log("something went wrong in set on click");
            }
        }
    };
    private void MenuSlide_ConnectID(){
        tvAbout= (TextView) findViewById(R.id.tvAbout);
        ivAvatar= (ImageView) findViewById(R.id.ivAvatar);
        linProfile= (LinearLayout) findViewById(R.id.linProfile);
        linCourse= (LinearLayout) findViewById(R.id.linCourses);
        linHistory= (LinearLayout) findViewById(R.id.linHistory);
        linLearn= (LinearLayout) findViewById(R.id.linLearn);
        linCommunity= (LinearLayout) findViewById(R.id.linCommunity);
    }
    private void MenuSlide_SetonClick(){
        tvAbout.setOnClickListener(menuClick);
        ivAvatar.setOnClickListener(menuClick);
        linProfile.setOnClickListener(menuClick);
        linLearn.setOnClickListener(menuClick);
        linHistory.setOnClickListener(menuClick);
        linCourse.setOnClickListener(menuClick);
        linCommunity.setOnClickListener(menuClick);
    }
    private void MenuSlide_DesignLayout(){
        Glide.with(context).load("https://graph.facebook.com/"+ V.USER.getFbId()+"/picture?type=large").into(ivAvatar);
    }

    //region Back Press
    private int countBackPress = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.menu_slide);
        context = this;
        getSlidingMenu().setBehindWidth(MyConvert.DpToPx(250, context));
        T = new ToastManager(context);
        myDB = new TinyDB(context);
        F = new FontManager(context);
        progressDialog = new SpotsDialog(context, R.style.CustomProgressDialog);
        //////////////////////
        initMenuSlide();
        ////////////////
        /*InitActivity();
        ConnectID();
        SetOnClick();
        DesignLayout();
        MainWork();*/
    }
    private void initMenuSlide(){
        MenuSlide_ConnectID();
        MenuSlide_SetonClick();
        MenuSlide_DesignLayout();
    }
    public void closepDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    public void BackPress() {
        if (countBackPress == 0) {
            countBackPress++;
            T.show(PRESS_BACK_AGAIN_TO_QUIT);
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
