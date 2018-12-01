/*
 *  Class Name: OfflineTools
 *
 *  Version: Version 1.0
 *
 *  Date: November 28, 2018
 *
 *  Copyright (c) Team 12, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behaviour at the University of Alberta
 */

package com.example.jerry.healemgood.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Date;

/**
 * Handles Offline mechanics
 * @author joeyUalberta
 * @version 1.0
 * @since 1.0
 */

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

    /**
     * return true if the device dont have internet connection
     * @return
     */
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
