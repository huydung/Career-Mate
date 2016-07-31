package com.careermate.CORE_LIBS.VIEWS;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by TuAnh on 5/18/2016.
 */
public class DefaultFontEditText extends EditText {
    public DefaultFontEditText(Context context) {
        super(context);
        init();
    }

    public DefaultFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DefaultFontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init(){
        Typeface tf= Typeface.createFromAsset(getContext().getAssets(),ViewConfig.FONT_DEFAULT);
        setTypeface(tf);
    }
}
