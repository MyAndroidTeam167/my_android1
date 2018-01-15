package com.example.hp.farmapp.Settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.farmapp.CalendarPackage.LandingActivity;
import com.example.hp.farmapp.Login.ForgetPass.FrgtPassActivity;
import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import java.util.Locale;
import java.util.logging.LoggingPermission;


public class SettingActivity extends AppCompatActivity implements View.OnClickListener,
AdapterView.OnItemSelectedListener  {

    Context context;
    Toolbar mActionBarToolbar;
    String[] country = {"English","Hindi"};
    String setlanguage;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context=this;

        TextView tvlogout= (TextView) findViewById(R.id.logout_setting);
        tvlogout.setOnClickListener(this);
        TextView tvsetlanguage=(TextView)findViewById(R.id.tvsetlanguage);
        tvsetlanguage.setOnClickListener(this);
        TextView tvchangepass=(TextView)findViewById(R.id.tvchangepass);
        tvchangepass.setOnClickListener(this);

        TextView title=(TextView)findViewById(R.id.tittle);
        title.setText(R.string.setting);
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        //getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
       // Toast.makeText(getApplicationContext(),country[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logout_setting) {

            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SharedPreferencesMethod.clear(context);
                            SharedPreferencesMethod.setBoolean(context, "Login", false);
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            finish();                        }
                    })
                    .setNegativeButton("No", null)
                    .show();


        }

        if(v.getId()==R.id.tvchangepass){
            Intent intent=new Intent(context, FrgtPassActivity.class);
            intent.putExtra("chanage_pass","from_setting");
            startActivity(intent);
            finish();
        }
        if (v.getId() == R.id.tvsetlanguage) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.my_dialog_layout, null);
            dialogBuilder.setView(dialogView);
            final Spinner spinner = (Spinner) dialogView.findViewById(R.id.mySpinnersetlang);
            spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) context);
            ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,country);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(aa);

            /*AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();*/

            dialogBuilder.setCancelable(true);
            dialogBuilder.setMessage("Change Language");
            dialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    setlanguage=spinner.getSelectedItem().toString();



                    if(setlanguage.equals("Hindi")){
                        changeLang("hi");
                        Log.e("Lang :",setlanguage);
                        String languageselected = setlanguage;
                        SharedPreferencesMethod.setString(context,SharedPreferencesMethod.LANGUAGE,languageselected);
                        Intent intent = new Intent(context, SettingActivity.class);
                        startActivity(intent);
                        finish();
                        //dialog.cancel();
                    }
                    else if(setlanguage.equals("English")){
                        changeLang("en");
                        Log.e("Lang :",setlanguage);
                        String languageselected = setlanguage;
                        SharedPreferencesMethod.setString(context,SharedPreferencesMethod.LANGUAGE,languageselected);
                        Intent intent = new Intent(context, SettingActivity.class);
                        startActivity(intent);
                        finish();
                       // dialog.cancel();
                    }
                    else{
                        Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }

                }

            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
//                        finish();
                    dialog.cancel();
                }
            });
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setMessage("Want to submit your response?");
            alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }

            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
//                        finish();
                    dialog.cancel();
                }
            });
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }*/
        }
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


}
