package com.example.hp.farmapp.FarmData;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.LandingActivity;
import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 15/12/17.
 */
public class FarmSoilTestActivity extends AppCompatActivity {
    Context context;
    Toolbar mActionBarToolbar;
    Button mButton;
    TextView tvphValue,tvphRating,tvEcValue,tvEcRating,tvOcValue,tvOcRating;
    TextView tvnValue,tvnRating,tvpValue,tvpRating,tvkValue,tvkRating;
    TextView tvsValue,tvsRating,tvznValue,tvznRating,tvbValue,tvbRating;
    TextView tvfeValue,tvfeRating,tvmnValue,tvmnRating,tvcuValue,tvcuRating;
    final String REGISTER_URL = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmApp/fetch_soil_card";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState){
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_test);
        context=this;

        TextView title=(TextView)findViewById(R.id.tittle);
        title.setText("Soil Test");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        mButton=(Button)findViewById(R.id.button2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,InspectorSoilCardInputActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvphValue = (TextView)findViewById(R.id.pHTestValUnit);
        tvphRating = (TextView)findViewById(R.id.pHRating);
        tvEcValue = (TextView)findViewById(R.id.ecTestValUnit);
        tvEcRating = (TextView)findViewById(R.id.ecRating);
        tvOcValue = (TextView)findViewById(R.id.ocTestValUnit);
        tvOcRating = (TextView)findViewById(R.id.ocRating);
        tvnValue = (TextView)findViewById(R.id.nTestValUnit);
        tvnRating = (TextView)findViewById(R.id.nRating);
        tvpValue = (TextView)findViewById(R.id.pTestValUnit);
        tvpRating = (TextView)findViewById(R.id.pRating);
        tvkValue = (TextView)findViewById(R.id.kTestValUnit);
        tvkRating = (TextView)findViewById(R.id.kRating);
        tvsValue = (TextView)findViewById(R.id.sTestValUnit);
        tvsRating = (TextView)findViewById(R.id.sRating);
        tvznValue = (TextView)findViewById(R.id.znTestValUnit);
        tvznRating = (TextView)findViewById(R.id.znRating);
        tvbValue = (TextView)findViewById(R.id.bTestValUnit);
        tvbRating = (TextView)findViewById(R.id.bRating);
        tvfeValue = (TextView)findViewById(R.id.feTestValUnit);
        tvfeRating = (TextView)findViewById(R.id.feRating);
        tvmnValue = (TextView)findViewById(R.id.mnTestValUnit);
        tvmnRating = (TextView)findViewById(R.id.mnRating);
        tvcuValue = (TextView)findViewById(R.id.cuTestValUnit);
        tvcuRating = (TextView)findViewById(R.id.cuRating);
        try{
            Log.e("checkArray","About to call GetText");
            GetText();
        }catch (JSONException e){
            e.printStackTrace();
        }
        //getSupportActionBar().setTitle("My Title");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
    public  void  GetText()  throws JSONException{
        Log.e("checkArray","Reached GetText()");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        JSONObject jsonObject =null;
                        JSONArray jsonArray = null;
//                        int size;
                        try{
                            jsonArray = new JSONArray(response);
                            int size = jsonArray.length();
                            String[] parameter = new String[12];
                            String[] value = new String[12];
                            String[] unit = new String[12];
                            String[] rating = new String[12];
                            String param,val,unit_val,rating_val;

                            for(int i=0;i<size;i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                param = jsonObject.getString("parameter");
                                val = jsonObject.getString("value");
                                unit_val = jsonObject.getString("unit");
                                rating_val = jsonObject.getString("rating");
                                parameter[i] = param;
                                value[i] = val;
                                unit[i] = unit_val;
                                rating[i] = rating_val;
                                Log.e("checkArray",param);
                            }
                            tvphValue.setText(value[0]);
                            tvphRating.setText(rating[0]);
                            tvEcValue.setText(value[1]+" "+unit[1]);
                            tvEcRating.setText(rating[1]);
                            tvOcValue.setText(value[2]+" "+unit[2]);
                            tvOcRating.setText(rating[2]);
                            tvnValue.setText(value[3]+" "+unit[3]);
                            tvnRating.setText(rating[3]);
                            tvpValue.setText(value[4]+" "+unit[4]);
                            tvpRating.setText(rating[4]);
                            tvkValue.setText(value[5]+" "+unit[5]);
                            tvkRating.setText(rating[5]);
                            tvsValue.setText(value[6]+" "+unit[6]);
                            tvsRating.setText(rating[6]);
                            tvznValue.setText(value[7]+" "+unit[7]);
                            tvznRating.setText(rating[7]);
                            tvbValue.setText(value[8]+" "+unit[8]);
                            tvbRating.setText(rating[8]);
                            tvfeValue.setText(value[9]+" "+unit[9]);
                            tvfeRating.setText(rating[9]);
                            tvmnValue.setText(value[10]+" "+unit[10]);
                            tvmnRating.setText(rating[10]);
                            tvcuValue.setText(value[11]+" "+unit[11]);
                            tvcuRating.setText(rating[11]);

                            Log.e("checkArray","This is response "+response);
//                           Log.e("checkArray","Reached End");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Log.e(TAG,error.toString());
                        Log.e("ERROR:",error.toString());

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                /*params.put(PARAMS_SAMPLE_NUM,strHealthCardNum);
                params.put(PARAMS_FARM_NUM,"5");*/
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
