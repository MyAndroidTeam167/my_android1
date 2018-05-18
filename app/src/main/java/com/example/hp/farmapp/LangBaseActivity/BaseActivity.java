package com.example.hp.farmapp.LangBaseActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    private Locale mCurrentLocale;

    @Override
    protected void onStart() {
        super.onStart();


        mCurrentLocale = getResources().getConfiguration().locale;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Locale locale = getLocale(this);

        if (!locale.equals(mCurrentLocale)) {
            mCurrentLocale = locale;
            recreate();
        }
    }

    public static Locale getLocale(Context context){
        //Sha sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        //String lang = SharedPreferencesMethod.getString(context,"lang");

        SharedPreferences pref = context.getSharedPreferences("MyPrefLang", 0);
        String lang=pref.getString("key_lang","");

        switch (lang) {
            case "English":
                lang = "en";
                break;
            case "Hindi":
                lang = "hi";
                break;
            case "Telgu":
                lang = "te";
                break;
        }
        return new Locale(lang);
    }
}