package com.careermate.CORE_LIBS.HELPER;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class MyConvert {
	public static int DpToPx(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi /160f);
	    return Math.round(px);
	}
	public static int PxToDp(float px, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return Math.round(dp);
	}
	public static int getWidthInPx(Context context){
		Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
		return Math.round(metrics.widthPixels);
	}
	public static int getHeightInPx(Context context){
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return Math.round(metrics.heightPixels);
	}
	public static String decodeUtf8(String st){
		try {
			//Log.d("tuanh","decode xau :"+ st);
			return URLDecoder.decode(st, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			//Log.d("tuanh","khong the decode xau :"+ st);
			return st;
		}
	}
	public static String encodeUtf8(String st){
		try {
			//Log.d("tuanh","decode xau :"+ st);
			return URLEncoder.encode(st, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			//Log.d("tuanh","khong the decode xau :"+ st);
			return st;
		}
	}

}
