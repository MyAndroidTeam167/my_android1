package com.example.hp.farmapp.Utiltiy;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by rahul on 12/2/2015.
 */
public class SharedPreferencesMethod {
    public static Context appContext;
    public static final String SHARED_PREFERENCE_NAME = "My_Farm_Shared_Preference";
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String first_name = "first_name";
    public static final String last_name = "last_name";
    public static final String EMAIL = "email_id";
    public static final String USER_ID = "user_id";
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String THEME_FLAG = "THEME_FLAG";
    public static final String BINDED = "USER_BINDED";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String DEVICE_ID="DEVICE_ID";
    public static final String SETAPCHECK="SETAPCHECK";
    public static final String COMP_AUDIO_REPLY="COMP_AUDIO_REPLY";
    public static final String COMP_LIVE_BACK_TECH_SUPPORT="COMP_LIVE_BACK_TECH_SUPPORT";
    public static final String COMP_IMAGES="COMP_IMAGES";
    public static final String COMP_GPS="COMP_GPS";
    public static final String COMP_OFFLINE_BACK_TECH_SUPPORT="COMP_OFFLINE_BACK_TECH_SUPPORT";
    public static final String COMP_FARM_COUNT="COMP_FARM_COUNT";
    public static final String COMP_INSPECTOR_COUNT="COMP_INSPECTOR_COUNT";
    public static final String COMP_WEATHER="COMP_WEATHER";
    public static final String COMP_LICENSE_PERIOD="COMP_LICENSE_PERIOD";
    public static final String COMP_MAX_FARMS="COMP_MAX_FARMS";






    /*public static final String PRIMARY_COLOR = "PRIMARY_COLOR";
    public static final String PRIMARY_COLOR_DARK = "PRIMARY_COLOR_DARK";
    public static final String ACCENT_COLOR = "ACCENT_COLOR";*/
    public static final String SELECT_THEME_URL = "SELECT_THEME_URL";

    public static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        return editor;
    }

    public static SharedPreferences getSharedPreference(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedpreferences;
    }

    public static boolean getBoolean(Context context, String name) {
        SharedPreferences sharedPreferences = getSharedPreference(context);
        return sharedPreferences.getBoolean(name, false);
    }

    public static void setStringSharedPreferencehistory(Context context, String name, Set<String> value) {
        appContext = context;
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        // editor.clear();
        editor.putStringSet(name, value);
        editor.commit();
    }

    public static Set<String> getStringSharedPreferenceshistory(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return settings.getStringSet(name, null);
    }

    public static void setBoolean(Context context, String name, boolean value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static String getString(Context context, String name) {
        SharedPreferences sharedPreferences = getSharedPreference(context);
        return sharedPreferences.getString(name, "");
    }

    public static void setString(Context context, String name, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(name, value);
        editor.commit();
    }

    public static int getInt(Context context, String name) {
        SharedPreferences sharedPreferences = getSharedPreference(context);
        return sharedPreferences.getInt(name, 0);
    }

    public static void setInt(Context context, String name, int value) {
        value=value+1;
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(name, value);
        editor.commit();
    }

    // for username string preferences
    public static void setDoubleSharedPreference(Context context, String name, double value) {
        appContext = context;
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(name, (float) value);
        editor.commit();
    }

    public static Double getDoubleSharedPreferences(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return (double) settings.getFloat(name, 0.0f);
    }

    public static void setLongSharedPreference(Context context, String name, long value) {
        appContext = context;
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(name, value);
        editor.commit();
    }

    public static long getLongSharedPreferences(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
        return settings.getLong(name, 0l);
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(SharedPreferencesMethod.SHARED_PREFERENCE_NAME);
        editor.clear();
        editor.commit();
        context.getSharedPreferences(SharedPreferencesMethod.SHARED_PREFERENCE_NAME, 0).edit().clear().commit();
    }

}
