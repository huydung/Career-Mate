package com.careermate.CORE_LIBS.UIMANAGERS;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.careermate.CORE_LIBS.HELPER.StringUtils;


/**
 * Created by TuAnh on 4/15/2016.
 */
public class ToastManager {
    public static final String TAG_DEBUG = LogManager.TAG;
    public static final boolean IS_DEBUG = LogManager.isDebug;

    private Context context;

    public ToastManager(Context context) {
        this.context = context;
    }

    public static String checkMessage(String mess) {
        if (StringUtils.isEmpty(mess)) return "string is null";
        return mess;

    }

    public void show(String message) {
        message = checkMessage(message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        log(message);
    }

    public void log(String message, boolean isShowToast) {
        if (isShowToast) {
            show(message);
        } else {
            log(message);
        }
    }

    public void log(String message) {
        message = checkMessage(message);
        if (IS_DEBUG) Log.d(TAG_DEBUG, message);
    }
}
