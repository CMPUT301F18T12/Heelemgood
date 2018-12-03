package com.example.jerry.healemgood.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.jerry.healemgood.config.AppConfig;

import static android.content.Context.MODE_PRIVATE;

/**
 * A helper class that do the store and retrieve from SharedPreference
 *
 * @author xiacijie
 * @version 1.0
 * @since 1.0
 */
public class SharedPreferenceUtil {

    /**
     * Store the value into shared preference
     *
     * @param context context
     * @param key key
     * @param value value
     */
    public static void store(Context context,String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences(AppConfig.MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.apply();
    }

    /**
     * retrieve the value from shared preference
     *
     * @param context context
     * @param key key
     *
     */
    public static String get(Context context,String key){
        SharedPreferences prefs = context.getSharedPreferences(AppConfig.MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString(key, null);
        return restoredText;
    }
}
