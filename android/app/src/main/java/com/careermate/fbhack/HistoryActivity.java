package com.careermate.fbhack;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.careermate.ACTIVITY.MenuActivity;
import com.careermate.CORE_LIBS.VIEWS.DefaultFontTextView;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by TuAnh on 7/31/2016.
 */
public class HistoryActivity extends MenuActivity {
    private DefaultFontTextView tvStatic;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private int[] LearnedTime= {
            10,9,0,12,20,
            30,20,25,0,26,
            18,20,21,10,15,
            21
    };
    private int n=16;
    private void demoDATA() {
        if(caldroidFragment==null)return;
        Calendar cal = Calendar.getInstance();
        ColorDrawable green = new ColorDrawable(Color.GREEN);
        ColorDrawable red = new ColorDrawable(Color.RED);
        L.log("here");
        cal.add(Calendar.DATE,1);
        for(int i=0;i<n;i++){
            cal.add(Calendar.DATE,-1);
            Date d= cal.getTime();
            if(LearnedTime[i]>0){
                caldroidFragment.setBackgroundDrawableForDate(green,d);
            }else{
                caldroidFragment.setBackgroundDrawableForDate(red,d);
            }

        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }
    private void doCreateCalendar(Bundle savedInstanceState){
        caldroidFragment = new CaldroidFragment();
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY);
            args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);
            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);
            caldroidFragment.setArguments(args);
        }
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        //region Listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
            }

            @Override
            public void onChangeMonth(int month, int year) {
            }

            @Override
            public void onLongClickDate(Date date, View view) {
            }

            @Override
            public void onCaldroidViewCreated() {
               demoDATA();
            }

        };
        caldroidFragment.setCaldroidListener(listener);
        //endregion
    }
    //region CORE_INIT
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_history);
        InitActivity();
        ConnectID();
        SetOnClick();
        DesignLayout();
        MainWork();
        doCreateCalendar(savedInstanceState);
    }

    @Override
    public void InitActivity() {
        setBackActivity(ProfileActivity.class);
    }

    @Override
    public void ConnectID() {
        tvStatic= (DefaultFontTextView) findViewById(R.id.tvStatic);
    }

    @Override
    public void SetOnClick() {

    }

    @Override
    public void DesignLayout() {
        int sum= 0;
        String content="";
        for(int i=0;i<LearnedTime.length;i++) sum+=LearnedTime[i];
        content= "Total Learning Hours: "+sum +" minutes\n";
        content+="Current Streak Days : 2 days\n";
        tvStatic.setText(content);
    }

    @Override
    public void MainWork() {
    }
    //endregion
}
