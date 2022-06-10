package com.example.adminnetflix.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.adminnetflix.models.TestModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    private static final String LIST_KEY = "list";
    public static void writeListInPref(Context context, List<String> list) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        SharedPreferences pref = context.getSharedPreferences("AdminSharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    public static List<String> readListFromPref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        List<String> list = gson.fromJson(jsonString, type);
        Log.i("do", String.valueOf(list));

        return list;
    }

}
