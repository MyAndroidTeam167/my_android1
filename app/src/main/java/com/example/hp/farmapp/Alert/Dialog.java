package com.example.hp.farmapp.Alert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.farmapp.Login.ForgetPass.Confirm.CnfrmPassActivity;
import com.example.hp.farmapp.R;

import java.util.ArrayList;

/**
 * Created by hp on 9/10/2017.
 */
public class Dialog {
    public static final int OTP = 1;
    public static final int FORGET_PASSWORD = 2;
    android.support.v7.app.AlertDialog.Builder builder;
    android.support.v7.app.AlertDialog alert;
    TextView tvfrgt,tvotp;
    String emailcnf,mobilecnf,passcnf;
    String mobileno;
    EditText etotp;

    public Dialog(final Context context, int flag, ArrayList<String> data) {
        if (flag == OTP) {
            otp(context, data);
        } else if (flag == FORGET_PASSWORD) {
            ForgetPassword(context, data);
        }


    }

    private void ForgetPassword(final Context context, ArrayList<String> data) {

        builder = new android.support.v7.app.AlertDialog.Builder(context);
        LayoutInflater _inflater = LayoutInflater.from(context);
        final View g = _inflater.inflate(R.layout.dialog_forgotpassword, null);
        builder.setView(g, 50, 50, 50, 50);
        alert = builder.create();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //tvfrgt=(TextView)((Activity)context).findViewById(R.id.forgettv);
        //String tvfrgtstr = ((TextView) g.findViewById(R.id.forgettv)).getText().toString().trim();
        ((TextView)g.findViewById(R.id.forgettv)).setText("Please Enter OTP to Reset Password");
        // etotp=(EditText) ((Activity)context).findViewById(R.id.otpet);



        ((Button) g.findViewById(R.id.canceldialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        ((Button) g.findViewById(R.id.dialogbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((EditText)g.findViewById(R.id.otpet)).getText().toString().trim().matches("12345"))
                {
                    Intent intent = new Intent(context, CnfrmPassActivity.class);
                    ((Activity)context).startActivity(intent);
                }
                else {
                    Toast.makeText((Activity)context, "Incorrect otp", Toast.LENGTH_LONG).show();
                }
                //((SignInActivity)context).callforgetAPI(email);
                }
        });



    }


    private void otp(final Context context, ArrayList<String> data) {

        builder = new android.support.v7.app.AlertDialog.Builder(context);
        LayoutInflater _inflater = LayoutInflater.from(context);
        final View g = _inflater.inflate(R.layout.dialog_forgotpassword, null);
        builder.setView(g, 50, 50, 50, 50);
        alert = builder.create();
        alert.setCancelable(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ((TextView)g.findViewById(R.id.otptv)).setText("Please Enter OTP to Login");





        //Your authentication key




        ((Button) g.findViewById(R.id.canceldialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        ((Button) g.findViewById(R.id.dialogbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               /* if (((EditText) g.findViewById(R.id.otpet)).getText().toString().trim().matches("12345")) {
                    emailcnf = DataHandler.newInstance().getLoginEmail();
                    mobilecnf = DataHandler.newInstance().getLoginmobileno();
                    passcnf = DataHandler.newInstance().getPassword();
                    SharedPreferencesMethod.setString(context, "Email", emailcnf);
                    SharedPreferencesMethod.setString(context, "Mobile", mobilecnf);
                    SharedPreferencesMethod.setString(context, "Password", passcnf);
                    SharedPreferencesMethod.setBoolean(context, "Login", true);

                    Intent intent = new Intent(context, HomeActivity.class);
                    ((Activity) context).startActivity(intent);
                } else {
                    Toast.makeText((Activity) context, "Incorrect otp", Toast.LENGTH_SHORT).show();
                }*/
                //hide();
                //((SignInActivity)context).callforgetAPI(email);
            }
        });
    }

    public void show() {
        if (!alert.isShowing())
            alert.show();
    }

    public void hide() {
        if (alert.isShowing())
            alert.dismiss();
    }

}