package com.careermate.CORE_LIBS.ANIMATIONS;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

/**
 * Created by TuAnh on 6/29/2016.
 */
public class MyScaleAnimation {
    private static final int TIME_SCALE_ANIMATION=300;
    private static final float START_SCALE=0.8f;
    private static final float END_SCALE=1.0f;
    public static void scale(View v){
        Animation scaleAnimation= new ScaleAnimation(START_SCALE,END_SCALE,START_SCALE,END_SCALE, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        scaleAnimation.setDuration(TIME_SCALE_ANIMATION);
        v.startAnimation(scaleAnimation);
    }
    public static void scale_repeat(View v){
        Animation scaleAnimation= new ScaleAnimation(START_SCALE,END_SCALE,START_SCALE,END_SCALE, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setDuration(TIME_SCALE_ANIMATION);
        v.startAnimation(scaleAnimation);
    }
    public static void scale_repeat(View v,long time){
        Animation scaleAnimation= new ScaleAnimation(START_SCALE,END_SCALE,START_SCALE,END_SCALE, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setDuration(time);
        v.startAnimation(scaleAnimation);
    }
    public static void scale(View v,float start,float end){
        Animation scaleAnimation= new ScaleAnimation(start,end,start,end, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        scaleAnimation.setDuration(TIME_SCALE_ANIMATION);
        v.startAnimation(scaleAnimation);
    }
    public static void scale_repeat(View v,float start,float end){
        Animation scaleAnimation= new ScaleAnimation(start,end,start,end, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setDuration(TIME_SCALE_ANIMATION);
        v.startAnimation(scaleAnimation);
    }
    public static void scale_repeat(View v,long time,float start,float end){
        Animation scaleAnimation= new ScaleAnimation(start,end,start,end, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setDuration(time);
        v.startAnimation(scaleAnimation);
    }

}
