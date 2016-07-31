package com.careermate.CORE_LIBS.HELPER;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.careermate.CORE_LIBS.UIMANAGERS.FontManager;
import com.careermate.fbhack.R;


/**
 * Created by TuAnh on 3/14/2016.
 */
public class CustomDialog {
    public static final int  BUTTON_YES=12;
    public static final int BUTTON_NO=13;
    public static int ID_CLICK=13;
    public static Dialog dialogYN;
    public static Dialog dialogY;
    public static Dialog dialoginfo;

    public static void infoShow(Context context,String str) {
        FontManager F= new FontManager(context);
        dialoginfo = new Dialog(context);
        dialoginfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialoginfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialoginfo.setContentView(R.layout.dialog_ok_only);
        dialoginfo.setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams wmlp = dialoginfo.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER ;


        TextView tvTitle,tvMess;
        Button btnOk;
        tvTitle= (TextView) dialoginfo.findViewById(R.id.tvTitle);
        tvMess= (TextView) dialoginfo.findViewById(R.id.tvMess);
        btnOk= (Button) dialoginfo.findViewById(R.id.btnOK);
        F.setFont(tvMess);
        F.setFont(tvTitle);
        F.setFont(btnOk);

        tvMess.setText(str);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialoginfo!=null) dialoginfo.dismiss();
            }
        });
        dialoginfo.show();
    }
    public static void infoShow(Context context,String str,String title) {
        FontManager F= new FontManager(context);
        dialoginfo = new Dialog(context);
        dialoginfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialoginfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialoginfo.setContentView(R.layout.dialog_ok_only);
        dialoginfo.setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams wmlp = dialoginfo.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER ;


        TextView tvTitle,tvMess;
        Button btnOk;
        tvTitle= (TextView) dialoginfo.findViewById(R.id.tvTitle);
        tvMess= (TextView) dialoginfo.findViewById(R.id.tvMess);
        btnOk= (Button) dialoginfo.findViewById(R.id.btnOK);
        F.setFont(tvMess);
        F.setFont(tvTitle);
        F.setFont(btnOk);
        tvTitle.setText(title);
        tvMess.setText(str);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialoginfo!=null) dialoginfo.dismiss();
            }
        });
        dialoginfo.show();
    }
    public static void infoShow(Context context,String str,boolean isShowTitle) {
        FontManager F= new FontManager(context);
        dialoginfo = new Dialog(context);
        dialoginfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialoginfo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialoginfo.setContentView(R.layout.dialog_ok_only);
        dialoginfo.setCanceledOnTouchOutside(false);

        WindowManager.LayoutParams wmlp = dialoginfo.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER ;


        TextView tvTitle,tvMess;
        Button btnOk;
        tvTitle= (TextView) dialoginfo.findViewById(R.id.tvTitle);
        tvMess= (TextView) dialoginfo.findViewById(R.id.tvMess);
        btnOk= (Button) dialoginfo.findViewById(R.id.btnOK);
        F.setFont(tvMess);
        F.setFont(tvTitle);
        F.setFont(btnOk);
        if(!isShowTitle) tvTitle.setVisibility(View.GONE);
        tvMess.setText(str);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialoginfo!=null) dialoginfo.dismiss();
            }
        });
        dialoginfo.show();
    }
    public static void YesNoShow(Context context,String str) {
        if(dialogYN!=null){
            dialogYN.dismiss();
            dialogYN=null;
        }
        FontManager F= new FontManager(context);
        dialogYN = new Dialog(context);
        dialogYN.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogYN.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogYN.setContentView(R.layout.dialog_yes_no);
        dialogYN.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams wmlp = dialogYN.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER ;


        TextView tvTitle,tvMess;
        Button btnYES,btnNO;
        tvTitle= (TextView) dialogYN.findViewById(R.id.tvTitle);
        tvMess= (TextView) dialogYN.findViewById(R.id.tvMess);
        btnYES= (Button) dialogYN.findViewById(R.id.btnYes);
        btnNO= (Button) dialogYN.findViewById(R.id.btnNo);
        F.setFont(tvMess);
        F.setFont(tvTitle);
        F.setFont(btnYES);
        F.setFont(btnNO);

        tvMess.setText(str);
        btnYES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_CLICK = BUTTON_YES;
                if (dialogYN != null) dialogYN.dismiss();
            }
        });
        btnNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_CLICK=BUTTON_NO;
                if(dialogYN!=null) dialogYN.dismiss();
            }
        });
        dialogYN.show();
    }
    public static void YesShow(Context context,String str) {
        if(dialogY!=null){
            dialogY.dismiss();
            dialogY=null;
        }
        FontManager F= new FontManager(context);
        dialogY = new Dialog(context);
        dialogY.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogY.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogY.setContentView(R.layout.dialog_ok_only);
        dialogY.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams wmlp = dialogY.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER ;


        TextView tvTitle,tvMess;
        Button btnYES;
        tvTitle= (TextView) dialogY.findViewById(R.id.tvTitle);
        tvMess= (TextView) dialogY.findViewById(R.id.tvMess);
        btnYES= (Button) dialogY.findViewById(R.id.btnOK);
        F.setFont(tvMess);
        F.setFont(tvTitle);
        F.setFont(btnYES);

        tvMess.setText(str);
        btnYES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogY != null) dialogY.dismiss();
            }
        });
        dialogY.show();
    }

}
