package com.example.hp.farmapp.Signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.PersonData.FillProfileActivity;
import com.example.hp.farmapp.Login.MainActivity;
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.R;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    Toolbar mActionBarToolbar;

    @Override
    public void onBackPressed() {
Intent intent=new Intent(context,MainActivity.class);
    startActivity(intent);
    finish();}

    private static final String REGISTER_URL = "http://spade.farm/app/index.php/signUp/insert_new_user";

    public static final String KEY_MOBILE = "mobNo";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_INSPECTOR = "is_inspector";
    ProgressDialog progressDialog;
    public static final String KEY_FARMER = "is_farmer";
    public static final String KEY_ADMIN = "is_admin";
    public static final String KEY_COMPANY_NUM = "token1"; //ctocken==>>company_num



    EditText namesignup, emailsignup, mobilesignup;
    ShowHidePasswordEditText passsignup,passsignupconfirm;
    Button registor,foradmin;
    TextView olduser;
    Context context;
    String isinspector = "N", isadmin = "N", isfarmer = "Y";
    String radiobuttontext,EmailSign,MobileSign,PassSign,Passsignconfirm,isadminsign="N",isinspectorsign,isfarmersign,rd11,rd22,rd33;
    String FinalEmail,finalmobile;
    RadioGroup radioGroup;
    Boolean smspermission=false;
    RadioButton rabioButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
   // private GoogleApiClient client;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=new Intent(context,MainActivity.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
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
        setContentView(R.layout.activity_sign_up);
        context = this;



        TextView title=(TextView)findViewById(R.id.tittle);
        title.setText("Register New Account");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

        //getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        //setTitle(getString(R.string.app_name));



            //namesignup = (EditText) findViewById(R.id.);
            emailsignup = (EditText) findViewById(R.id.emailsignup);
            mobilesignup = (EditText) findViewById(R.id.mobilesignup);
            passsignup = (ShowHidePasswordEditText) findViewById(R.id.signuppass);
            registor = (Button) findViewById(R.id.registerbutt);
            olduser = (TextView) findViewById(R.id.olduser);
           // radioGroup = (RadioGroup) findViewById(R.id.radio_group);
            passsignupconfirm = (ShowHidePasswordEditText) findViewById(R.id.signuppassconfirm);
            foradmin=(Button)findViewById(R.id.admincalender);


        //foradmin.setVisibility(View.GONE);
        olduser.setVisibility(View.GONE);

        foradmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,FillProfileActivity.class);
                startActivity(intent);
            }
        });



        /*if(smspermission==smsPermission()) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_SMS}, 1);
        }*/
//        radioGroup.clearCheck();


            // Toast.makeText(SignUpActivity.this, rabioButton.getText(), Toast.LENGTH_SHORT).show();


            registor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                   /* int selectedId = radioGroup.getCheckedRadioButtonId();
                    rabioButton = (RadioButton) findViewById(selectedId);
                    RadioButton rd1 = (RadioButton) findViewById(R.id.radio_inspector);
                    RadioButton rd2 = (RadioButton) findViewById(R.id.radio_admin);
                    RadioButton rd3 = (RadioButton) findViewById(R.id.radio_farmer);
                   */ PassSign = passsignup.getText().toString();
                    Passsignconfirm = passsignupconfirm.getText().toString();
                    /*rd11 = rd1.getText().toString();
                    rd22 = rd2.getText().toString();
                    rd33 = rd3.getText().toString();
                    radiobuttontext = rabioButton.getText().toString();
                    if (radiobuttontext == rd11) {
                        isinspector = 1;
                    } else if (radiobuttontext == rd33) {
                        isfarmer = 1;
                    } else if (radiobuttontext == rd22) {
                        isadmin = 1;
                    }
*/
                    //Toast.makeText(SignUpActivity.this, Integer.toString(isinspector), Toast.LENGTH_SHORT).show();

                    int length = passsignup.getText().toString().trim().length();
                    int length2 = passsignupconfirm.getText().toString().trim().length();

                    if (!emailValidator(emailsignup.getText().toString().trim())) {
                        emailsignup.setError(getString(R.string.invalid_email));
                    } else if (!isValidMobile(mobilesignup.getText().toString().trim())) {
                        mobilesignup.setError(getString(R.string.invalid_mobile_no));
                    } else if (!(length > 7 && length < 33)) {
                        passsignup.setError(getString(R.string.password_er));
                        passsignup.setText("");
                        //        Toast.makeText(MainActivity.this, length, Toast.LENGTH_SHORT).show();
                    } else if (!(length2 > 7 && length2 < 33)) {
                        passsignupconfirm.setError(getString(R.string.password_er));
                        passsignupconfirm.setText("");
                        //        Toast.makeText(MainActivity.this, length, Toast.LENGTH_SHORT).show();
                    } else if (!PassSign.equals(Passsignconfirm)) {
                        passsignup.setText("");
                        passsignupconfirm.setText("");
                        passsignup.setError(getString(R.string.samepassword));
                        passsignupconfirm.setError(getString(R.string.samepassword));
                    } else {
                   /* Intent intent = new Intent(context, ImageActivity.class);
                    startActivity(intent);
*/
                        try {
                            progressDialog = ProgressDialog.show(SignUpActivity.this,
                                    getString(R.string.dialog_please_wait), "");
                            // CALL GetText method to make post method call
                            GetText();
                        } catch (Exception ex) {
                            //content.setText(" url exeption! " );
                        }
                        //Toast.makeText(MainActivity.this, "yeah done", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            olduser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
       // new PostDataAsyncTask().execute();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.



   /* public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_inspector:
                if (checked)
                    isinspector = 1;
                break;
            case R.id.radio_farmer:
                if (checked)
                    isfarmer = 1;
                break;
            case R.id.radio_admin:
                if (checked)
                    isadmin = 1;
                // Pirates are the best
                break;
        }
    }*/


    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            // if(phone.length() < 6 || phone.length() > 13) {
            if (phone.length() != 10) {
                check = false;
                //txtPhone.setError("Not Valid Number");
            } else {
                if(phone.startsWith("0"))
                {check=false;}
                else{
                check = true;}
            }
        } else {
            check = false;
        }
        return check;
    }




    public  void  GetText()  throws JSONException
    {
        // Get user defined values
        EmailSign = emailsignup.getText().toString().trim();
        MobileSign   = mobilesignup.getText().toString().trim();
        PassSign   = passsignup.getText().toString().trim();
      /*  isadminsign   = Integer.toString(isadmin);
        isfarmersign   = Integer.toString(isfarmer);
        isinspectorsign   = Integer.toString(isinspector);
*/


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if(response.equals("\"Password And User added Successfully\""))
                        {


                            DataHandler.newInstance().setSignupMobile(MobileSign);
                            DataHandler.newInstance().setSignUpmail(EmailSign);
                            DataHandler.newInstance().setSignUpPassword(PassSign);
                            DataHandler.newInstance().setSignUp(true);
                            progressDialog.dismiss();

                            Intent intent=new Intent(context,MainActivity.class);
                            startActivity(intent);
                            finish();

                            //new Dialog(context , Dialog.OTP , null).show();

                            /*Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            finish();*/
                        }
                        else{

                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this,response,Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Log.e(TAG,error.toString());
                        progressDialog.dismiss();

                        Toast.makeText(SignUpActivity.this,error.toString(),Toast.LENGTH_LONG).show();


                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_MOBILE,MobileSign);
                params.put(KEY_PASSWORD,PassSign);
                params.put(KEY_EMAIL, EmailSign);
                params.put(KEY_INSPECTOR, "N");
                params.put(KEY_FARMER, "Y");
                params.put(KEY_ADMIN, "N");
                params.put(KEY_COMPANY_NUM,"1");

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean smsPermission()
    {
        String permission = "android.permission.READ_SMS";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}
