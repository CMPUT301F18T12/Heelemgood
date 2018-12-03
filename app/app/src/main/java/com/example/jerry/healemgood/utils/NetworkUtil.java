package com.example.jerry.healemgood.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Check whether Network is available or not
 * @author xiacijie
 * @version 1.0
 * @since 1.0
 */

public class NetworkUtil {
    //https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
    static public boolean isNetworkAvailable(Activity context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
