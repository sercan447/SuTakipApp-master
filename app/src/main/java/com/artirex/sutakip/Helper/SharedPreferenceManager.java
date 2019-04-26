package com.artirex.sutakip.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceManager {


    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private SharedPreferenceManager()
     {

    }

    public static SharedPreferences getSharedPrefere(Context context)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences;
    }
    public static SharedPreferences.Editor setSharedEdit(Context context)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();

        return editor;
    }
}
