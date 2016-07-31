package com.careermate.CORE_LIBS.UIMANAGERS;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by TuAnh on 4/26/2016.
 */
public class FontManager {
    public static final String FONT_DEFAULT= "fonts/default.ttf";
    private Context context;
    private Typeface tf;

    public FontManager(Context context) {
        this.context = context;
        tf= Typeface.createFromAsset(context.getAssets(),FONT_DEFAULT);
    }
    public  void setFont(TextView tv){
        tv.setTypeface(tf);
    }
    public  void setFont(EditText et){
        et.setTypeface(tf);
    }
    public  void setFont(Button btn){
        btn.setTypeface(tf);
    }
    public  void setFont(TextView tv,String fontname){
        Typeface tff= Typeface.createFromAsset(context.getAssets(),"fonts/"+fontname);
        tv.setTypeface(tff);
    }
    public  void setFont(EditText et,String fontname){
        Typeface tff= Typeface.createFromAsset(context.getAssets(),"fonts/"+fontname);
        et.setTypeface(tff);
    }
}
