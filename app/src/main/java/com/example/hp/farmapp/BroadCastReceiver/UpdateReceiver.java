package com.example.hp.farmapp.BroadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.CalendarTask.ShowTaskViewPagerActivity;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 07-03-2018.
 */

public class UpdateReceiver extends BroadcastReceiver {
    String user_num,farm_num,byfarmer,farmer_reply,fetch_id,netstatus;
    String SAVE_FARMER_RESPONSE_URL = "https://spade.farm/app/index.php/farmCalendar/set_farmer_reponse";
    final String DATA_ID = "data_id";
    final String FARMER_REPLY = "farmer_reply";

    @Override
    public void onReceive(Context context, Intent intent) {



        user_num=SharedPreferencesMethod.getString(context,"user_num");
        farm_num=SharedPreferencesMethod.getString(context,"farm_num");
        farmer_reply=SharedPreferencesMethod.getString(context,"farmer_reply");
        fetch_id=SharedPreferencesMethod.getString(context,"farmdayworknum");
        netstatus=SharedPreferencesMethod.getString(context,"Netstatus");
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();
        if (isConnected) {
            Log.i("MYFARMNET", "connected" + isConnected);

            if (netstatus.equals("gone")) {

                SharedPreferencesMethod.getEditor(context).putString("Netstatus","goneee").apply();

                StringRequest stringRequestNew = new StringRequest(Request.Method.POST, SAVE_FARMER_RESPONSE_URL,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("check", "This is resp" + response);
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("checkArray", error.toString());
                                /*NetworkResponse networkResponse = error.networkResponse;
                                Log.e("checkArray", String.valueOf(networkResponse.statusCode));
                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                    Log.e("checkArray", "timeout error");

                                } else if (error instanceof AuthFailureError) {
                                    Log.e("checkArray", "authfailure error");

                                } else if (error instanceof ServerError) {
                                    Log.e("checkArray", "Server error");

                                } else if (error instanceof NetworkError) {
                                    //TODO
                                    Log.e("checkArray", "network error");

                                } else if (error instanceof ParseError) {
                                    Log.e("checkArray", "parse error");
                                    //TODO
                                }*/
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        String AUDIO = "audio_file";
                        String USERNUM = "user_num";
                        String FARMNUM = "farm_num";
                        String BYFARMER = "by_farmer";
                        String BYADMIN = "by_admin";
                        String BYINSPECTOR = "by_inspector";
                        params.put(BYADMIN, "N");
                        params.put(BYFARMER, "Y");
                        params.put(BYINSPECTOR, "N");
                        Log.e("check", "Reached Params");
                        if (user_num != null) {
                            params.put(USERNUM, user_num);
                        }
                        if (farm_num != null) {
                            params.put(FARMNUM, farm_num);
                        }
                        if (farmer_reply != null) {
                            params.put(FARMER_REPLY, farmer_reply);
//                                        Log.e("check", "REached params response = " + response);
                        }

                        if (fetch_id != null) {
                            params.put(DATA_ID, fetch_id);
                        }

                        return params;
                    }
                };

                RequestQueue requestQueueNew = Volley.newRequestQueue(context);
                requestQueueNew.add(stringRequestNew);

            }
            else{

            }
        }


        else {Log.i("MYFARMNET", "not connected" +isConnected);}
    }
}
