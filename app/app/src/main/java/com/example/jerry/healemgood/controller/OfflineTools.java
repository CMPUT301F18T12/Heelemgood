package com.example.jerry.healemgood.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Date;

public class OfflineTools {
    private static Context context=null;

    /**
     * Pass in the Application context to the offline tool
     * which will allow it to check network status
     * @param c Application Context
     */
    public static void setContext(Context c){
        context=c;
    }

    /**
     * Wait for network connection
     * See https://stackoverflow.com/questions/18375962/how-to-check-that-android-phone-is-not-connected-to-internet
     */
    public static void waitForConnection(){
        if(context!=null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            while (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                try {
                    Thread.sleep(2000);
                }catch(InterruptedException e){Log.d("Name-Jeff","cant sleep");}
                Log.d("Name-Jeff","waiting for network"+new Date().toString());
                activeNetworkInfo = connectivityManager
                        .getActiveNetworkInfo();
            }
        }
    }
    public static boolean checkForConenction(){
        if(context!=null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return false;
            }
        }
        return true;
    }
}
