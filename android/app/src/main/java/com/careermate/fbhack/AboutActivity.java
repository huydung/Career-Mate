package com.careermate.fbhack;

import android.os.Bundle;

import com.careermate.CORE_LIBS.ACTIVITY.ActivityForm;

/**
 * Created by TuAnh on 7/31/2016.
 */
public class AboutActivity extends ActivityForm {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.ac_about);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void InitActivity() {
        setBackActivity(ProfileActivity.class);
    }

    @Override
    public void ConnectID() {

    }

    @Override
    public void SetOnClick() {

    }

    @Override
    public void DesignLayout() {

    }

    @Override
    public void MainWork() {

    }
}
