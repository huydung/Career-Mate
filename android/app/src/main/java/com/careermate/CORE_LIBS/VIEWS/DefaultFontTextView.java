package com.careermate.CORE_LIBS.VIEWS;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by TuAnh on 5/18/2016.
 */
public class DefaultFontTextView extends TextView {
    public DefaultFontTextView(Context context) {
        super(context);
        init();
    }

    public DefaultFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DefaultFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init(){
        Typeface tf= Typeface.createFromAsset(getContext().getAssets(),ViewConfig.FONT_DEFAULT);
        setTypeface(tf);
    }
    public void setUnderLine(){
        if(getText()==null) {
            return;
        }
        String text=getText().toString();
        SpannableString sp= new SpannableString(text);
        sp.setSpan(new UnderlineSpan(),0,text.length(),0);
        setText(sp);
    }

}
