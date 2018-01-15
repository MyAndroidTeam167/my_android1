package com.example.hp.farmapp.FarmData;

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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.PersonData.FillProfileActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InspectorSoilCardInputActivity extends AppCompatActivity {
    Context context;
    Toolbar mActionBarToolbar;
    Button submitCard;
    EditText healthCardNum, collectionDate, laboratoryName;
    String strHealthCardNum, strCollectionDate, strLaboratoryName;
    String[] Parameter, Unit, Rating;

    String strtestValue1, strtestValue2, strtestValue3, strtestValue4, strtestValue5, strtestValue6, strtestValue7, strtestValue8, strtestValue9, strtestValue10, strtestValue11, strtestValue12;
    //    Spinner cuRating, cuUnit, mnRating, mnUnit, feRating, feUnit, bRating, bUnit, znRating, znUnit, sRating, sUnit;
//    Spinner kRating, kUnit, pRating, pUnit, nRating, nUnit, ocRating, ocUnit, ecRating, ecUnit, phRating, phUnit;
    EditText phTestValue, ecTestValue, ocTestValue, nTestValue, pTestValue, kTestValue, sTestValue, znTestValue, bTestValue, feTestValue, MnTestValue, cuTestValue;

    JSONArray jsonParameter;
    final String PARAMS_TEST_VALUE_1 = "test_value_1";
    final String PARAMS_TEST_VALUE_2 = "test_value_2";
    final String PARAMS_TEST_VALUE_3 = "test_value_3";
    final String PARAMS_TEST_VALUE_4 = "test_value_4";
    final String PARAMS_TEST_VALUE_5 = "test_value_5";
    final String PARAMS_TEST_VALUE_6 = "test_value_6";
    final String PARAMS_TEST_VALUE_7 = "test_value_7";
    final String PARAMS_TEST_VALUE_8 = "test_value_8";
    final String PARAMS_TEST_VALUE_9 = "test_value_9";
    final String PARAMS_TEST_VALUE_10 = "test_value_10";
    final String PARAMS_TEST_VALUE_11 = "test_value_11";
    final String PARAMS_TEST_VALUE_12 = "test_value_12";
    String PARAMS;
    String testVal;

    String[] TestValue;

    final String PARAMS_FARM_NUM = "farm_num";
    final String PARAMS_SAMPLE_NUM = "sample_num";
    final String PARAMS_COLLECTION_DATE = "collection_date";
    final String PARAMS_LABORATORY="laboratory";
    //    final String PARAMS_PARAMETER = "parameter";
    final String PARAMS_TEST_VALUE = "test_value";
    final String PARAMS_UNIT = "unit";
    final String PARAMS_RATING = "rating";
    final String REGISTER_URL="https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmApp/inspector_soil_card_input";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(context, FarmSoilTestActivity.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_soil_card_input);
        context = this;

        TextView title = (TextView) findViewById(R.id.tittle);
        title.setText("Enter Soil Card");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        submitCard = (Button) findViewById(R.id.submitCard);
        healthCardNum = (EditText) findViewById(R.id.healthCardNum);
        collectionDate = (EditText) findViewById(R.id.collectionDate);
        laboratoryName = (EditText) findViewById(R.id.laboratoryName);

        phTestValue = (EditText) findViewById(R.id.phTestValue);
        ecTestValue = (EditText) findViewById(R.id.ECTestValue);
        ocTestValue = (EditText) findViewById(R.id.OCTestValue);
        nTestValue = (EditText) findViewById(R.id.NTestValue);
        pTestValue = (EditText) findViewById(R.id.PTestValue);
        kTestValue = (EditText) findViewById(R.id.KTestValue);
        sTestValue = (EditText) findViewById(R.id.STestValue);
        znTestValue = (EditText) findViewById(R.id.ZnTestValue);
        bTestValue = (EditText) findViewById(R.id.BTestValue);
        feTestValue = (EditText) findViewById(R.id.FeTestValue);
        MnTestValue = (EditText) findViewById(R.id.MnTestValue);
        cuTestValue = (EditText) findViewById(R.id.CuTestValue);

        submitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Button Clicked",Toast.LENGTH_SHORT).show();
                TestValue= new String[12];

                TestValue[0] = phTestValue.getText().toString().trim();
                TestValue[1] = ecTestValue.getText().toString().trim();
                TestValue[2] = ocTestValue.getText().toString().trim();
                TestValue[3] = nTestValue.getText().toString().trim();
                TestValue[4] = pTestValue.getText().toString().trim();
                TestValue[5] = kTestValue.getText().toString().trim();
                TestValue[6] = sTestValue.getText().toString().trim();
                TestValue[7] = znTestValue.getText().toString().trim();
                TestValue[8] = bTestValue.getText().toString().trim();
                TestValue[9] = feTestValue.getText().toString().trim();
                TestValue[10] = MnTestValue.getText().toString().trim();
                TestValue[11] = cuTestValue.getText().toString().trim();
                strHealthCardNum = healthCardNum.getText().toString().trim();
                strCollectionDate = collectionDate.getText().toString().trim();
                strLaboratoryName = laboratoryName.getText().toString().trim();
                try {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setCancelable(true);
                    alertDialogBuilder.setMessage("Want to submit your Soil Health Card?");
                    alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try{
                                GetText(TestValue);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                        finish();
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    public  void  GetText(String[] TestValue)  throws JSONException
    {
        final String[] TestValueArr = TestValue;
//        Log.e("checkArray","print testValueArr"+TestValueArr[0]+TestValueArr[1]+TestValueArr[2]+TestValueArr[3]);
        // Get user defined values
//        this.TestValue=TestValue;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        JSONObject jsonObject =null;
//                        JSONArray jsonArray = null;
//                        int size;
                        try{
//                            response = response.replace("\"","").trim();
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
                        Toast.makeText(InspectorSoilCardInputActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.e("ERROR:",error.toString());

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(PARAMS_LABORATORY,strLaboratoryName);
                params.put(PARAMS_COLLECTION_DATE,strCollectionDate);
                params.put(PARAMS_SAMPLE_NUM,strHealthCardNum);
                params.put(PARAMS_FARM_NUM,"5");
                for(int i=1;i<=12;i++){
                    PARAMS = "PARAMS_TEST_VALUE_"+i;
                    testVal = "test_value_"+i;
                    PARAMS = testVal;
//                    Log.e("checkArray",PARAMS+"Test Value = "+TestValueArr[i-1]);
                    params.put(PARAMS,TestValueArr[i-1]);
                }
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}