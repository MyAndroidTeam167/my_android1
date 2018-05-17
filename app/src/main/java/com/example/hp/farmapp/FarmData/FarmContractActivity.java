package com.example.hp.farmapp.FarmData;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.Adapter.SpinnerAdapter;
import com.example.hp.farmapp.CalendarPackage.LandingActivity.LandingActivity;
import com.example.hp.farmapp.CalendarPackage.NavGetterSetter.SpinnerData;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 15/12/17.
 */

public class FarmContractActivity extends AppCompatActivity {

    Context context;
    Toolbar mActionBarToolbar;
    Button test;
    ConnectivityManager connectivityManager;
    boolean connected = false;
    Boolean is_binded=false;
    ProgressDialog progressDialog;
    private static final String URL_FETCH_CONTRACT = "http://spade.farm/app/index.php/farmApp/fetch_contract";
    WebView wv_contract_form;
    String ct1,farm_num="";
    TextView no_conract_farm;

    @Override
    public void onBackPressed(){
       super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green_new));
        }
        super.onCreate(savedInstanceState);
        context=this;
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else {
            connected = false;
        }
        if (connected) {
            is_binded = SharedPreferencesMethod.getBoolean(context, SharedPreferencesMethod.BINDED);
            if (is_binded) {
                setContentView(R.layout.activity_contract);
        TextView title=(TextView)findViewById(R.id.tittle);
        wv_contract_form=(WebView)findViewById(R.id.wv_contract_form);
        no_conract_farm=(TextView)findViewById(R.id.tv_no_contract_farm);
        farm_num=SharedPreferencesMethod.getString(context,"farm_num");
        ct1=SharedPreferencesMethod.getString(context,"cctt");
                progressDialog = ProgressDialog.show(FarmContractActivity.this,
                        getString(R.string.dialog_please_wait), "");
                CallApi();
        title.setText(getString(R.string.farm_contract_title));
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }else
        {
            setContentView(R.layout.not_binded_layout);
            basic_title();
        }
    }else{
            setContentView(R.layout.internet_not_connencted);
            basic_title();
        }
    }

    private void CallApi() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FETCH_CONTRACT,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jobject = null;
                            try {
                                jobject = new JSONObject(response);
                                String status = jobject.getString("status");
                               String msg=jobject.getString("msg");
                                if (status.equals("0")) {
                                    progressDialog.dismiss();
                                    wv_contract_form.setVisibility(View.GONE);
                                    no_conract_farm.setVisibility(View.VISIBLE);

                                } else {
                                    wv_contract_form.setVisibility(View.VISIBLE);
                                    no_conract_farm.setVisibility(View.GONE);
                                    String result=jobject.getString("farm_contract");
                                    JSONObject resultobject = new JSONObject(result);
                                    String contract_link=resultobject.getString("contract");
                                    Toast.makeText(context, contract_link, Toast.LENGTH_SHORT).show();
                                    wv_contract_form.setWebViewClient(new MyBrowser());
                                    String url = "http://docs.google.com/gview?embedded=true&url="+contract_link;
                                    wv_contract_form.getSettings().setLoadsImagesAutomatically(true);
                                    wv_contract_form.getSettings().setLoadWithOverviewMode(true);
                                    wv_contract_form.getSettings().setUseWideViewPort(true);
                                    wv_contract_form.getSettings().setBuiltInZoomControls(true);
                                    wv_contract_form.getSettings().setSupportZoom(true);
                                    wv_contract_form.getSettings().setDisplayZoomControls(true);
                                    wv_contract_form.getSettings().setJavaScriptEnabled(true);
                                    wv_contract_form.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                                    wv_contract_form.loadUrl(url);

                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                             Log.e("error",error.toString());
                            Toast.makeText(context, R.string.error_text, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("token1",ct1);
                    params.put("farm_num",farm_num);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

//        return SpinnerDatums;



    }

    void basic_title(){
        TextView title=(TextView)findViewById(R.id.tittle);
        title.setText(getString(R.string.farm_contract_title));
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            wv_contract_form.setVisibility(View.VISIBLE);
            /*if (!(about_us_prog.getVisibility() ==View.VISIBLE)) {
                about_us_prog.setVisibility(View.VISIBLE);
                // pd.show();
            }*/
            progressDialog.show();
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            wv_contract_form.setVisibility(View.VISIBLE);
           /* if ((about_us_prog.getVisibility() ==View.VISIBLE)) {
                about_us_prog.setVisibility(View.GONE);
                // pd.dismiss();
            }*/
           progressDialog.dismiss();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
           /* if ((about_us_prog.getVisibility() ==View.VISIBLE)) {
                about_us_prog.setVisibility(View.GONE);
            }*/
            wv_contract_form.setVisibility(View.GONE);
        }
    }

}
