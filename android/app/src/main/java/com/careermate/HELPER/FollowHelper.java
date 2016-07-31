package com.careermate.HELPER;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class FollowHelper {
    public static Intent newFacebookIntent(Context context, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }
    public static Intent getFacebookIntent(String fbUri,String fbUrl,Context context){
        Intent intent;
        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse(fbUri));
                    //Uri.parse("fb://profile/100004345687129")); //Trys to make intent with FB's URI
            //Uri.parse("fb://groups/163215593699283")); //Trys to make intent with FB's URI
        } catch (Exception e) {
            intent= new Intent(Intent.ACTION_VIEW,
                    Uri.parse(fbUrl)); //catches and opens a url to the desired page
        }
        return  intent;
    }
    public static final String[] URL_BUSINESS={"https://www.facebook.com/profile.php?id=100006185475682&fref=ts","https://www.facebook.com/phamnhatvuong1967?fref=ts"};
    public static final String[] URL_EDU={"https://www.facebook.com/sutantokarta","https://www.facebook.com/zuck?fref=ts"};
    public static final String[] URL_FOOD={"https://www.facebook.com/umg.zfvoivoh","https://www.facebook.com/huydung1406"};
    public static final String[] URL_IT={"https://www.facebook.com/connie.yw.ho","https://www.facebook.com/kevin.f.lin","https://www.facebook.com/lynn.cherngchaosil","https://www.facebook.com/sutantokarta","https://www.facebook.com/zuck?fref=ts"};
    public static final String[] URL_POLITICIAN={"https://www.facebook.com/connie.yw.ho","https://www.facebook.com/kevin.f.lin"};
    public static final String[] URL_SERVICES={"https://www.facebook.com/ngansau?fref=nf","https://www.facebook.com/tranlethanhnhu?fref=nf"};
    public static final String[] URL_SCIENTEST={"https://www.facebook.com/kevin.f.lin","https://www.facebook.com/zuck?fref=ts"};
    public static String[][] URL_FOLLOW={
            URL_BUSINESS,
            URL_EDU,
            URL_FOOD,
            URL_IT,
            URL_POLITICIAN,
            URL_SERVICES,
            URL_SCIENTEST
    };
    public static final String[] NAME_BUSINESS={"Đoàn Nguyên Đức","Phạm Nhật Vượng"};
    public static final String[] NAME_EDU={"Karta Sutanto ","Mark Zuckerberg"};
    public static final String[] NAME_FOOD={"Anh Tú Hoàng","Nguyễn Huy Dũng"};
    public static final String[] NAME_IT={"Connie Ho","Kevin Lin","Lynn Cherngchaosil","Karta Sutanto ","Mark Zuckerberg"};
    public static final String[] NAME_POLITICIAN={"Connie Ho","Kevin Lin"};
    public static final String[] NAME_SERVICES={"Le Huynh Kim Ngan","Tran Le Thanh Nhu",};
    public static final String[] NAME_SCIENTEST={"Kevin Lin","Mark Zuckerberg"};
    public static String[][] NAME_FOLLOW={
            NAME_BUSINESS,
            NAME_EDU,
            NAME_FOOD,
            NAME_IT,
            NAME_POLITICIAN,
            NAME_SERVICES,
            NAME_SCIENTEST
    };

    ///SKILLS
    public static String[] NAME_FOLLOW_GROUP={
            "Business",
            "Cook",
            "English",
            "Media",
            "People",
            "Presentation",
            "Programming",
            "Research"
    };
    public static String[] URI_FOLLOW_GROUP={
            "1058897484180159",
            "628563447306959",
            "185082008573975",
            "313269092346198",
            "522282831312287",
            "172055559880602",
            "488858381319904",
            "1782800008663952"
    };
    public static String[] URL_FOLLOW_GROUP={
            "https://www.facebook.com/groups/1058897484180159/",
            "https://www.facebook.com/groups/628563447306959/",
            "https://www.facebook.com/groups/185082008573975/",
            "https://www.facebook.com/groups/313269092346198/",
            "https://www.facebook.com/groups/522282831312287/",
            "https://www.facebook.com/groups/172055559880602/",
            "https://www.facebook.com/groups/488858381319904/",
            "https://www.facebook.com/groups/1782800008663952/"

};


}
