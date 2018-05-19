package com.example.hp.farmapp.FarmImages;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.FarmData.ShowFarmActivity;
import com.example.hp.farmapp.LangBaseActivity.BaseActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowAllFarmImagesActivity extends BaseActivity {

    private static final String REGISTER_URL = "https://spade.farm/app/index.php/farmApp/fetch_farm_images";
    public static final String KEY_FARM_NUM = "farm_num";
    private List<FarmImageGetterSetter> farm_image_list;
    FarmImageAdapter farmImageListAdapter;
    private LinearLayoutManager mLayoutManager;
    Context context;
    String farm_num = "";
    String ct1 = "";
    final String KEY_TOKEN = "token2";
    Toolbar mActionBarToolbar;
    ConnectivityManager connectivityManager;
    boolean connected = false;
    Boolean is_binded = false;
    TextView no_farm_images;
    ProgressDialog progressDialog;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    RecyclerView recy_show_farm_images;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green_new));
        }
        super.onCreate(savedInstanceState);
        context = this;
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
                setContentView(R.layout.activity_show_all_farm_images);
                init();
                TextView title = (TextView) findViewById(R.id.tittle);
                title.setText(getString(R.string.activities_images_title));
                mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
                setSupportActionBar(mActionBarToolbar);
                ct1 = SharedPreferencesMethod.getString(context, "cctt");
                no_farm_images = (TextView) findViewById(R.id.no_farm_images);


                //getSupportActionBar().setTitle("My Title");

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                }

                progressDialog = ProgressDialog.show(ShowAllFarmImagesActivity.this,
                        getString(R.string.dialog_please_wait),"");
                AsyncTaskRunner runnermainact = new AsyncTaskRunner();
                runnermainact.execute();
            } else {
                setContentView(R.layout.not_binded_layout);
                basic_title();
            }
        } else {
            setContentView(R.layout.internet_not_connencted);
            basic_title();
        }
    }

    private void init() {
        recy_show_farm_images = (RecyclerView) findViewById(R.id.recycler_for_show_farm_images);
        farm_num = SharedPreferencesMethod.getString(context, "farm_num");

    }


    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        public AsyncTaskRunner() {
            super();
        }


        @Override
        protected String doInBackground(final String... params) {

            try {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,

                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONArray jsonarray = null;
                                farm_image_list = new ArrayList<>();
                                FarmImageGetterSetter farm_image_lists;
                                try {
                                    jsonarray = new JSONArray(response);
                                    String farm_image_link = null;

                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);

                                        farm_image_link = jsonobject.getString("img_link");
                                        Log.e("Tag", farm_image_link);
                                        farm_image_lists = new FarmImageGetterSetter();
                                        farm_image_lists.setFarm_image_link(farm_image_link);
                                        farm_image_list.add(farm_image_lists);


                                    }

                                    progressDialog.dismiss();
                                    farmImageListAdapter = new FarmImageAdapter(farm_image_list, context);
                                    recy_show_farm_images.setHasFixedSize(true);
                                    recy_show_farm_images.setAdapter(farmImageListAdapter);
                                    farmImageListAdapter.notifyDataSetChanged();
                                    mLayoutManager = new LinearLayoutManager(context);
                                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    recy_show_farm_images.setLayoutManager(mLayoutManager);

                                   /* recyclerViewSoilTest.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewSoilTest, new RecyclerTouchListener.ClickListener() {
                                        @Override
                                        public void onClick(View view, int position) {
                                            final GetterSetterForSoil soillists = SoilTestList.get(position);
                                            Intent intent =new Intent(context,InspectorSoilCardInputActivity.class);
                                            intent.putExtra("from","soilnotdone");
                                            DataHandler.newInstance().setFarmnum(soillists.getSoil_test_farm_num());
                                            //intent.putExtra("soil_farm_num",soillists.getSoil_test_farm_num());
                                            startActivity(intent);
                                            finish();
                                            }
                                        @Override
                                        public void onLongClick(View view, int position) {
                                        }
                                    }));*/
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                                    farmImageListAdapter = new FarmImageAdapter(farm_image_list, context);
                                    if (farmImageListAdapter.getItemCount() == 0) {
                                        no_farm_images.setVisibility(View.VISIBLE);

/*
                                        Toast.makeText(context, "No Images", Toast.LENGTH_SHORT).show();
*/
                                    }
                                    recy_show_farm_images.setAdapter(farmImageListAdapter);
                                    progressDialog.dismiss();
                                }
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Log.e("Error",error.toString());
                                Toast.makeText(ShowAllFarmImagesActivity.this, R.string.error_text, Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(KEY_FARM_NUM, farm_num);
                        params.put(KEY_TOKEN, ct1);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            } catch (Exception e) {
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    void basic_title() {
        TextView title = (TextView) findViewById(R.id.tittle);
        title.setText(getString(R.string.activities_images_title));
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
}
