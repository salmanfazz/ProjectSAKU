package com.example.mockupsai;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveState {

    public static void Save(Context context, String name, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String Read(Context context, String name, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, value);
    }
}
