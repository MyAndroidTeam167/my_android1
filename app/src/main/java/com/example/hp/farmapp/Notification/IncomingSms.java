package com.example.hp.farmapp.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

/**
 * Created by hp on 9/12/2017.
 */
public class IncomingSms extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    String newmesage;

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getMessageBody();

                    message = message.substring(0, Math.min(message.length(), 6));
                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                    if(senderNum.contains("TFCTOR")) {
                        Intent myIntent = new Intent("otp");
                        myIntent.putExtra("message", message);
                        //SharedPreferencesMethod.setString(context, "sms", message);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
                    }
                    // Show Alert

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
}

