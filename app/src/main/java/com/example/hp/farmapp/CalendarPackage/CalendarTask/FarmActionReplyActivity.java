package com.example.hp.farmapp.CalendarPackage.CalendarTask;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.net.rtp.RtpStream;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.ChatBox.ChatActivity;
import com.example.hp.farmapp.FarmData.FarmPackage.FarmImagesActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.TRUE;

public class FarmActionReplyActivity extends AppCompatActivity {
    Toolbar mActionBarToolbar;
    Context context;
    ImageView imgView;
    CheckBox mCheckBox;
    Button mButton,next_button;
    EditText mEditText;
    String sTaskStatus;
    TextView allMessagesLink;
    final String DATA_ID = "data_id";
    final String DATA_TASK_DATE = "data_task_date";
    final String DATA_TASK_STATUS = "task_done_status";
    final String FARMER_REPLY = "farmer_reply";
    final String KEY_TOKEN="token4";
    String ct1="";

    String fetch_id, description,farmerReply, chemical, chemical_qty, compulsary, activity, img_link, done, date_of_task,imgs_quantity;
    String REGISTER_URL = "https://spade.farm/app/index.php/farmCalendar/fetch_farm_day_data_by_id";
    String SAVE_FARMER_RESPONSE_URL = "https://spade.farm/app/index.php/farmCalendar/set_farmer_reponse";

    TextView tvFarmerReply, tvactivityName, tvactivityDescription, tvactivityDate, tvchemical, tvqtychemical, tvCompulsary;

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    String encoded="";
    private String farm_num;
    private String user_num;

    private Button recordButton,playButton;
    boolean mStartRecording,mStartPlaying;
    final String API_URL = "https://spade.farm/app/index.php/farmApp/save_audio_response";
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    ProgressDialog progressDialog;
    private int flag = 0;
    File file;
    String type;
    String id,task_date;
    LinearLayout audio_linear,farmer_reply_linear,farmer_reply_edit_text_linear,linear_is_done;
    String is_audio_reply="";
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent intent=new Intent(context,LandingActivity.class);
//        startActivity(intent);
//        finish();
       /* if(type.equals("from_calendar")){
            Intent intent=new Intent(context,ShowTaskActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent=new Intent(context,ShowTaskViewPagerActivity.class);
            startActivity(intent);
            finish();
        }*/
       super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
       /* if(type.equals("from_calendar")){
            Intent intent=new Intent(context,ShowTaskActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent=new Intent(context,ShowTaskViewPagerActivity.class);
            startActivity(intent);
            finish();
        }*/
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_action_reply);
        Log.e("check", "reached onCreate");
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green_new));
        }
        context = this;


        is_audio_reply=SharedPreferencesMethod.getString(context,SharedPreferencesMethod.COMP_AUDIO_REPLY);

        TextView title = (TextView) findViewById(R.id.tittle);
        title.setText(R.string.activity);
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        ct1=SharedPreferencesMethod.getString(context,"cctt");
        tvactivityName = (TextView) findViewById(R.id.reply_activity_name_value);
        tvactivityDescription = (TextView) findViewById(R.id.reply_activity_description_value);
        tvactivityDate = (TextView) findViewById(R.id.reply_activity_date_value);
        tvchemical = (TextView) findViewById(R.id.reply_activity_chemical_name_value);
        tvqtychemical = (TextView) findViewById(R.id.reply_activity_quantity_value);
        tvCompulsary = (TextView) findViewById(R.id.reply_activity_compulsary_value);
        imgView = (ImageView) findViewById(R.id.img_view);
        mCheckBox = (CheckBox) findViewById(R.id.reply_done_status);
        mButton = (Button) findViewById(R.id.button_submit);
        mEditText = (EditText) findViewById(R.id.et_farmer_reply);
        tvFarmerReply = (TextView) findViewById(R.id.tv_farmer_reply);
        allMessagesLink=(TextView)findViewById(R.id.view_all_messages);
        next_button=(Button)findViewById(R.id.button_next);
        linear_is_done=(LinearLayout)findViewById(R.id.linear_is_done_lay);
        audio_linear=(LinearLayout)findViewById(R.id.linear_audio_lay);
        farmer_reply_edit_text_linear=(LinearLayout)findViewById(R.id.linear_farmer_reply);
        farmer_reply_linear=(LinearLayout)findViewById(R.id.linear_show_text_msg);

        next_button.setVisibility(View.GONE);

        //getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        user_num = SharedPreferencesMethod.getString(context,"UserNum");
        farm_num=SharedPreferencesMethod.getString(context,"farm_num");
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
              id = extras.getString("id");
              task_date = extras.getString("task_date");
              type=extras.getString("type");
        }

        //Toast.makeText(context, id, Toast.LENGTH_SHORT).show();

        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";

        if(is_audio_reply.equals("1")){
            audio_linear.setVisibility(View.VISIBLE);
        }else{
            audio_linear.setVisibility(View.GONE);
        }

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        recordButton = (Button)findViewById(R.id.recordButton);
        playButton=(Button)findViewById(R.id.playButton);
        recordButton.setText(R.string.record_player);
        playButton.setText(R.string.play_player);
        mStartRecording = TRUE;
        mStartPlaying = TRUE;
        if(flag==0){
            playButton.setClickable(false);
            playButton.setEnabled(false);
            playButton.setBackgroundColor(Color.parseColor("#808080"));
        }

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecord(mStartRecording);
                flag=1;
                playButton.setClickable(true);
                playButton.setEnabled(true);
                playButton.setBackgroundColor(Color.parseColor("#238E68"));
                if(mStartRecording) {
                    recordButton.setText(R.string.stop_player);
                } else {
                    recordButton.setText(R.string.record_player);
                }
                mStartRecording = !mStartRecording;
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    playButton.setText(R.string.stop_player);
                } else {
                    playButton.setText(R.string.play_player);
                }
                mStartPlaying = !mStartPlaying;
            }
        });

//        Toast.makeText(getApplicationContext()," "+id+" "+task_date,Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Log.e("check","got response");
                            JSONObject jobject = null;
                            String date_today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                            Log.e("check", "reached try block");
                            jobject = new JSONObject(response);
                            fetch_id = jobject.getString("farm_dwork_num");
                            activity = jobject.getString("activity");
                            compulsary = jobject.getString("is_compulsary");
                            img_link = jobject.getString("img_link");
                            chemical = jobject.getString("chemical_to_use");
                            chemical_qty = jobject.getString("quantity_of_chemical");
                            done = jobject.getString("is_done");
                            date_of_task = jobject.getString("date");
                            description = jobject.getString("activity_description");
                            imgs_quantity=jobject.getString("images_qty");
                            farmerReply = jobject.getString("farmer_reply");
                            Log.e("Check", "id" + id + " " + "img_link" + " " + img_link);
                            tvactivityName.setText(activity);
                            tvactivityDescription.setText(description);
                            tvactivityDate.setText(date_of_task);
                            tvchemical.setText(chemical);
                            tvqtychemical.setText(chemical_qty + " lit/acre");
                            if (compulsary.equals("Y")) {
                                tvCompulsary.setText(R.string.yes_text);
                            } else {
                                tvCompulsary.setText(R.string.no_text);
                            }
                            Uri uri = Uri.parse(img_link);
                            Picasso.with(context).load(uri).into(imgView);
                            if (done.equals("Y")) {
                                mCheckBox.setChecked(true);
                                mCheckBox.setClickable(false);
                                mButton.setClickable(false);
                                mButton.setVisibility(View.GONE);
                                next_button.setVisibility(View.GONE);
                                mButton.setBackgroundColor(Color.parseColor("#808080"));
                                mEditText.setVisibility(View.GONE);
                                tvFarmerReply.setText(farmerReply);
                            } else {
                                mCheckBox.setChecked(false);
                                tvFarmerReply.setVisibility(View.GONE);
                                if(date_of_task.compareTo(date_today)<=0){
                                }else{
                                    mButton.setVisibility(View.GONE);
                                    next_button.setVisibility(View.GONE);
                                    linear_is_done.setVisibility(View.GONE);
                                    audio_linear.setVisibility(View.GONE);
                                    farmer_reply_edit_text_linear.setVisibility(View.GONE);
                                    farmer_reply_linear.setVisibility(View.GONE);
                                }
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                if (id != null) {
                    params.put(DATA_ID, id);
                    Log.e("check", "id passed" + " " + id);
                }
                if (task_date != null) {
                    params.put(DATA_TASK_DATE, task_date);
                    Log.e("check", "date passed" + " " + task_date);
                }
                if(ct1!=null){
                    params.put(KEY_TOKEN,ct1);
                }
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file = new File(mFileName);
                int size = (int)file.length();
                Log.e("checkArray","Size of audio byte file = "+size);
                byte[] bytes = new byte[size];
                try {
                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                    buf.read(bytes, 0, bytes.length);
                    buf.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                encoded = Base64.encodeToString(bytes, 0);
                final String response = mEditText.getText().toString().trim();
                if(!isResponseFilled(response)){
//                            Toast.makeText(context,"Please Enter Comment",Toast.LENGTH_LONG).show();
                    mEditText.setError(getString(R.string.error_enter_response));

                }else {
                    if(mCheckBox.isChecked()){
                        sTaskStatus = "Y";

                    }else{
                        sTaskStatus = "N";

                    }
                    Intent intent=new Intent(context, FarmImagesActivity.class);
                        intent.putExtra("user_num",user_num);
                        intent.putExtra("farm_num",farm_num);
                        intent.putExtra("audio_file",encoded);
                        intent.putExtra("fetch_id",fetch_id);
                        intent.putExtra("date_of_task",date_of_task);
                        intent.putExtra("s_task_status",sTaskStatus);
                        intent.putExtra("farmer_reply",response);
                        intent.putExtra("imgs_quntatity",imgs_quantity);
                        startActivity(intent);
                        finish();

                }

                }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file = new File(mFileName);
                int size = (int)file.length();
                Log.e("checkArray","Size of audio byte file = "+size);
                byte[] bytes = new byte[size];
                try {
                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                    buf.read(bytes, 0, bytes.length);
                    buf.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                encoded = Base64.encodeToString(bytes, 0);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setMessage(R.string.submit_response);
                alertDialogBuilder.setPositiveButton(R.string.submit_option, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(mCheckBox.isChecked()){
                            sTaskStatus = "Y";

                        }else{
                            sTaskStatus = "N";

                        }

                        final String response = mEditText.getText().toString().trim();
                        if(!isResponseFilled(response)){
                            dialog.cancel();
//                            Toast.makeText(context,"Please Enter Comment",Toast.LENGTH_LONG).show();
                            mEditText.setError(getString(R.string.error_enter_response));

                        }else {
                            StringRequest stringRequestNew = new StringRequest(Request.Method.POST, SAVE_FARMER_RESPONSE_URL,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.e("check","This is resp"+response);
                                            Toast.makeText(context, R.string.action_submitted, Toast.LENGTH_LONG).show();
                                            boolean deleted = file.delete();
                                            Intent intent=new Intent(context,ShowTaskViewPagerActivity.class);
                                            //intent.putExtra("Type","all_activities");
                                            startActivity(intent);
                                            finish();
                                            /*try {
                                                JSONObject jobject = null;
                                                jobject = new JSONObject(response);
//                                                Log.e("check",response);
////                                                Log.e("check", "today date " + jobject.getString("today_date"));
//                                                Toast.makeText(context, "Action Submitted", Toast.LENGTH_SHORT).show();
//                                                boolean deleted = file.delete();
//                                                Intent intent=new Intent(context,ShowTaskActivity.class);
//                                                intent.putExtra("Type","all_activities");
//                                                startActivity(intent);
//                                                finish();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }*/
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("checkArray",error.toString());
                                            Toast.makeText(context, R.string.error_text, Toast.LENGTH_LONG).show();
                                            NetworkResponse networkResponse = error.networkResponse;
                                            Log.e("checkArray",String.valueOf(networkResponse.statusCode));
                                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                                Log.e("checkArray","timeout error");

                                            } else if (error instanceof AuthFailureError) {
                                                Log.e("checkArray","authfailure error");

                                            } else if (error instanceof ServerError) {
                                                Log.e("checkArray","Server error");

                                            } else if (error instanceof NetworkError) {
                                                //TODO
                                                Log.e("checkArray","network error");

                                            } else if (error instanceof ParseError) {
                                                Log.e("checkArray","parse error");
                                                //TODO
                                            }
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
                                    params.put(BYADMIN,"N");
                                    params.put(BYFARMER,"Y");
                                    params.put(BYINSPECTOR,"N");
                                    params.put(KEY_TOKEN,ct1);
                                    Log.e("check","Reached Params");
                                    if(user_num!=null){
                                        params.put(USERNUM,user_num);
                                    }
                                    if(farm_num!=null){
                                        params.put(FARMNUM,farm_num);
                                    }
                                    if (response != null) {
                                        params.put(FARMER_REPLY, response);
//                                        Log.e("check", "REached params response = " + response);
                                    }
                                    if (sTaskStatus != null) {
                                        params.put(DATA_TASK_STATUS, sTaskStatus);
                                    }
                                    if (fetch_id != null) {
                                        params.put(DATA_ID, fetch_id);
                                    }
                                    if (date_of_task != null) {
                                        params.put(DATA_TASK_DATE, date_of_task);
                                    }
                                    if(encoded!=null){
                                        params.put(AUDIO,encoded);
                                    }
                                    return params;
                                }
                            };

                            RequestQueue requestQueueNew = Volley.newRequestQueue(context);
                            requestQueueNew.add(stringRequestNew);


                        }
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
            }
        });
        allMessagesLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"Open Chat Box",Toast.LENGTH_LONG).show();
                Log.e("check","open chat box");
                Intent intent=new Intent(context,ChatActivity.class);
                intent.putExtra("fetchId",fetch_id);
                startActivity(intent);
                //finish();
            }
        });
       /* Uri uri = Uri.parse();
        Picasso.with(context).load(uri).into(imgView);*/
    }



    public boolean isResponseFilled(String text){
        boolean flag = true;
        if(text.isEmpty()){
            flag=false;
        }
        if(text.equals("null")){
            flag=false;
        }
        if(text.equals(" ")){
            flag=false;
        }
        return flag;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }
    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();
    }
    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    public void onStop() {
        super.onStop();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void checkbox1_clicked(View v) {
        if (mCheckBox.isChecked()) {
            //mButton.setVisibility(View.GONE);
            if(imgs_quantity.equals("0")){
                mButton.setVisibility(View.VISIBLE);
                next_button.setVisibility(View.GONE);
            }else{
                mButton.setVisibility(View.GONE);
                next_button.setVisibility(View.VISIBLE);
            }

        } else {
            mButton.setVisibility(View.VISIBLE);
            next_button.setVisibility(View.GONE);
        }


    }
}