package com.example.adminnetflix.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class StoreUtil {
    public static SharedPreferences sharedPreferences;

    public static void save(Context context, String key, String values) {
        sharedPreferences = context.getSharedPreferences("AdminSharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(key, values);
        myEdit.commit();
    }

    public static String get(Context context, String key) {
        sharedPreferences = context.getSharedPreferences("AdminSharedPref", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

}
