package com.careermate.CORE_LIBS.ANIMATIONS;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Created by TuAnh on 6/29/2016.
 */
public class MyRotationAnimation {
    private static final int TIME_ROTATION_ANIMATION=300;
    private static final float START_ROTATION=0.0f;
    private static final float END_ROTATION=360.0f;
    public static void rotation(View v){
        Animation rotationAnimation= new RotateAnimation(START_ROTATION,END_ROTATION,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotationAnimation.setInterpolator(new OvershootInterpolator());
        rotationAnimation.setDuration(TIME_ROTATION_ANIMATION);
        v.startAnimation(rotationAnimation);
    }
    public static void rotation_repeat(View v){
        Animation rotationAnimation= new RotateAnimation(START_ROTATION,END_ROTATION,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotationAnimation.setInterpolator(new OvershootInterpolator());
        rotationAnimation.setDuration(TIME_ROTATION_ANIMATION);
        rotationAnimation.setRepeatMode(Animation.REVERSE);
        rotationAnimation.setRepeatCount(Animation.INFINITE);
        v.startAnimation(rotationAnimation);
    }
    public static void rotation_repeat(View v,long time){
        Animation rotationAnimation= new RotateAnimation(START_ROTATION,END_ROTATION,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotationAnimation.setInterpolator(new OvershootInterpolator());
        rotationAnimation.setDuration(time);
        rotationAnimation.setRepeatMode(Animation.REVERSE);
        rotationAnimation.setRepeatCount(Animation.INFINITE);
        v.startAnimation(rotationAnimation);
    }
    public static void rotation(View v,float start,float end){
        Animation rotationAnimation= new RotateAnimation(start,end,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotationAnimation.setInterpolator(new OvershootInterpolator());
        rotationAnimation.setDuration(TIME_ROTATION_ANIMATION);
        v.startAnimation(rotationAnimation);
    }
    public static void rotation_repeat(View v,float start,float end){
        Animation rotationAnimation= new RotateAnimation(start,end,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotationAnimation.setInterpolator(new OvershootInterpolator());
        rotationAnimation.setDuration(TIME_ROTATION_ANIMATION);
        rotationAnimation.setRepeatMode(Animation.REVERSE);
        rotationAnimation.setRepeatCount(Animation.INFINITE);
        v.startAnimation(rotationAnimation);
    }
    public static void rotation_repeat(View v,long time,float start,float end){
        Animation rotationAnimation= new RotateAnimation(start,end,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotationAnimation.setInterpolator(new OvershootInterpolator());
        rotationAnimation.setDuration(time);
        rotationAnimation.setRepeatMode(Animation.REVERSE);
        rotationAnimation.setRepeatCount(Animation.INFINITE);
        v.startAnimation(rotationAnimation);
    }

}
