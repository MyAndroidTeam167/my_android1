package com.example.hp.farmapp.Signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hp.farmapp.Company.CompanyActivity;
import com.example.hp.farmapp.LangBaseActivity.BaseActivity;
import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import java.util.Locale;

public class SetLanguageActivity extends BaseActivity implements View.OnClickListener {
    Context context;
    TextView textView1, textView2, textView3;
    String languageselected = "";
    Toolbar mActionBarToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green_new));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_language);
        context = this;
        TextView title=(TextView)findViewById(R.id.tittle);
        title.setText("Select Language");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);



        //getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null){
           /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);*/
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Toast.makeText(context, SharedPreferencesMethod.getString(context, "lang"), Toast.LENGTH_SHORT).show();
        init();
    }

    private void init() {

        textView1 = (TextView) findViewById(R.id.lang_hindi);
        textView2 = (TextView) findViewById(R.id.lang_english);
        textView3 = (TextView) findViewById(R.id.lang_telgu);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
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
        if (v.getId() == R.id.lang_hindi) {
            changeLang("hi");
            languageselected = "Hindi";
            Set_id_for_future(languageselected);
            //SharedPreferencesMethod.setString(context, "lang", "Hindi");
            Intent intent = new Intent(context, MainActivity.class);
            Toast.makeText(context, R.string.welcome, Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.lang_english) {
            changeLang("en");
            languageselected = "English";
            Set_id_for_future(languageselected);

            //SharedPreferencesMethod.setString(context, "lang", "English");
            Toast.makeText(context, R.string.welcome, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();

        }
        else if (v.getId() == R.id.lang_telgu) {
            changeLang("te");
            languageselected = "Telgu";
            Set_id_for_future(languageselected);
            //SharedPreferencesMethod.setString(context, "lang", "Telgu");
            Toast.makeText(context, R.string.welcome, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }

    private void Set_id_for_future(String language) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefLang", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key_lang",language.trim()); // Storing string
        editor.commit();
    }

}
