package com.example.hp.farmapp.Signup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import java.util.Locale;

public class SetLanguageActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    TextView textView1,textView2  ;
    String languageselected="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_language);
        setTitle("Select Language");
        init();
    }

    private void init() {
        context = this;

        textView1 = (TextView) findViewById(R.id.w_espanol);
        textView2 = (TextView) findViewById(R.id.w_skip);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
    }

    private void changeLang(String lang) {

        Locale myLocale;
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.w_espanol) {
            changeLang("hi");
            languageselected="Hindi";
            SharedPreferencesMethod.setString(context,SharedPreferencesMethod.LANGUAGE,languageselected);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }

        else if (v.getId() == R.id.w_skip){
            changeLang("en");
            languageselected="English";
            SharedPreferencesMethod.setString(context,SharedPreferencesMethod.LANGUAGE,languageselected);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }


}
