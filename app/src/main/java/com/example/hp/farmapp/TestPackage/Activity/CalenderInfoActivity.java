package com.example.hp.farmapp.TestPackage.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Signup.SignUpActivity;

public class CalenderInfoActivity extends AppCompatActivity {

    WebView wv1;
    ProgressDialog pd;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_calender_info);
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        context=this;

       // b1=(Button)findViewById(R.id.button);
        //ed1=(EditText)findViewById(R.id.editText);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        wv1=(WebView)findViewById(R.id.calenderinfo);
        pd = new ProgressDialog(CalenderInfoActivity.this);
        pd.setMessage("Please wait Loading...");
        pd.show();
        wv1.setWebViewClient(new MyBrowser());

                String url = "http://spade.farm/app/index.php/farmCalendar";
                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setLoadWithOverviewMode(true);
                wv1.getSettings().setUseWideViewPort(true);
                wv1.getSettings().setBuiltInZoomControls(true);

                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent=new Intent(context,CalenderInfoActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(context,SignUpActivity.class);
        startActivity(intent);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            if (!pd.isShowing()) {
                pd.show();
            }
            return true;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            if (pd.isShowing()) {
                pd.dismiss();
            }

        }
    }



}
