package com.careermate.CORE_LIBS.VIEWS;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by TuAnh on 5/19/2016.
 */
public class ListDrawableImageView extends ImageView {
    private int clickState,defaultState;
    private boolean isClick;
    public ListDrawableImageView(Context context) {
        super(context);
    }

    public ListDrawableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListDrawableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void updateBackGround(boolean isClick){
        if(isClick){
            setBackgroundResource(this.clickState);
        }else{
            setBackgroundResource(this.defaultState);
        }
    }
    public void setState(int clickState,int defaultState,boolean isClick){
        this.clickState=clickState;
        this.defaultState=defaultState;
        this.isClick=isClick;
        updateBackGround(isClick);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        isClick=!isClick;
        updateBackGround(isClick);
    }
}
