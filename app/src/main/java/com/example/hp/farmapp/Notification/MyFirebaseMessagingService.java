package com.example.hp.farmapp.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.hp.farmapp.Response.ImageActivity;
import com.example.hp.farmapp.Beans.GetProfile;
import com.example.hp.farmapp.Database.DatabaseHandler;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.NotificationUtils;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;

import com.example.hp.farmapp.app.Config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by hp on 9/4/2017.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    private ScheduledExecutorService scheduleTaskExecutor;


    Context context;
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        context = this;




        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        remoteMessage.getData();
//        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());


        try {
            sendNotification((remoteMessage.getData()),remoteMessage.getSentTime());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



/*


        if (remoteMessage == null)
            return;


        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());

            String click_action = remoteMessage.getNotification().getClickAction();
            Intent in = new Intent(click_action);
        }

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());


                handleDataMessage(remoteMessage.getData());

        }
     */
/*   if (remoteMessage.getNotification()!=null) {
            Log.d(TAG,"Message body:" +remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }*//*


*/
    }

    private void handleDataMessage(Map<String, String> data) {
        Log.e(TAG, "push json: " + data.toString());


            //JSONObject data = json.getJSONObject("data");

            String title = data.get("title");
            String message = data.get("message");
            String isBackground = data.get("is_background");
            String imageUrl = data.get("image");
            String timestamp = data.get("timestamp");
            String payload = data.get("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), ImageActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }


    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }
    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
           // Log.e(TAG,message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{

            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            // Log.e(TAG,message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
            // If the app is in background, firebase itself handles the notification
        }
    }


    private void sendNotification(Map<String, String> msg, long sentTime) throws JSONException, IOException {

        //JSONObject data = msg.getJSONObject("data");

        String message=msg.get("message");

        String flowers=msg.get("image");
        String title=msg.get("title");
        long time=sentTime;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        String formattedDate = df.format(c.getTime());        //Bitmap icon = null;


        int i,j ;

        i=SharedPreferencesMethod.getInt(context,"count");




        if(message!=null)
        {
            SharedPreferencesMethod.setInt(context,"count",i);
        }

        j=SharedPreferencesMethod.getInt(context,"count");

        //i=SharedPreferencesMethod.setInt();
        //DataHandler.newInstance().setMessage(message);


            SharedPreferencesMethod.setString(context,"message"+Integer.toString(j),message);

        DatabaseHandler db = new DatabaseHandler(this);
        db.addNotification(new GetProfile(message,title,formattedDate));


        //String title = data.getString("title");
        //String message = data.getString("message");
        /*boolean isBackground = data.getBoolean("is_background");
        String imageUrl = data.getString("image");
        String timestamp = data.getString("timestamp");
        JSONObject payload = data.getJSONObject("payload");*/
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);


        android.support.v4.app.NotificationCompat.InboxStyle inboxStyle = new android.support.v4.app.NotificationCompat.InboxStyle();


        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getPackageName() + "/raw/notification");
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        //Notification notification =notificationBuilder.setSmallIcon(R.mipmap.alert)
Notification notification;
                notification=notificationBuilder.setSmallIcon(R.drawable.noti_32).setStyle(new NotificationCompat.BigTextStyle().bigText(message+flowers))
                .setWhen(0).setContentTitle(title)
                .setContentText(message)
                .setContentInfo(formattedDate)
                .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setColor(getResources().getColor(R.color.colorAccent))
                .setSound(alarmSound)
                .setContentIntent(pendingIntent).build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        //DataHandler.newInstance().setMessage(message);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(m, notification);

    }
}


