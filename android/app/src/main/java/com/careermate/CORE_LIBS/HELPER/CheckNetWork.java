package com.careermate.CORE_LIBS.HELPER;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by TuAnh on 7/1/2016.
 */
public class CheckNetWork {
    public static boolean CheckNetWork(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        try {
                            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                            urlc.setRequestProperty("User-Agent", "Test");
                            urlc.setRequestProperty("Connection", "close");
                            urlc.setConnectTimeout(500); // choose your own
                            // timeframe
                            urlc.setReadTimeout(500); // choose your own
                            // timeframe
                            urlc.connect();
                            int networkcode2 = urlc.getResponseCode();
                            return (urlc.getResponseCode() == 200);
                        } catch (IOException e) {
                            return (false); // connectivity exists, but no
                            // internet.
                        }
                    }

        }
        return false;
    }
}
