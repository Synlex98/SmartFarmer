package com.fibo.smartfarmer.db;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    public static boolean isBooleanValueTrue(Context context, String key){
        SharedPreferences preferences=context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        return preferences.getBoolean(key,true);
    }

    public static void putBooleanValue(Context context,String key,boolean value){
        SharedPreferences preferences=context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
}
