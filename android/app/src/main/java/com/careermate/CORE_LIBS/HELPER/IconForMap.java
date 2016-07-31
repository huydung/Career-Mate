package com.careermate.CORE_LIBS.HELPER;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by TuAnh on 4/23/2016.
 */
public class IconForMap {
    public static Bitmap get_icon(Resources res,int resourcesID){
        Bitmap tmp;
        tmp= BitmapFactory.decodeResource(res, resourcesID);
        return Bitmap.createScaledBitmap(tmp,72,72,false);
    }
}
