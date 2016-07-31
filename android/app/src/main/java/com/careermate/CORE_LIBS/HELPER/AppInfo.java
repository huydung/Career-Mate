package com.careermate.CORE_LIBS.HELPER;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import com.careermate.CORE_LIBS.UIMANAGERS.LogManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by TuAnh on 7/29/2016.
 */
public class AppInfo {
    public static LogManager L= LogManager.getINSTANCE();
    public static String getKeyHash(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return "KeyHash:" + Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {
            return "error name not found!";
        } catch (NoSuchAlgorithmException e) {
            return "error!";
        }
        return "null";
    }
}
