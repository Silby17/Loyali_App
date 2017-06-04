package com.silbytech.loyali.gcm;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.silbytech.loyali.MainMenuActivity;
import com.silbytech.loyali.R;


public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!bundle.isEmpty() && GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            String msg = (String) bundle.get("message");
            String title = (String) bundle.get("nTitle");
            String company = (String) bundle.get("nCompany");
            String trackingNumber = (String) bundle.get("nTracking_number");
            String status = (String) bundle.get("nStatus");

            //SaveInDatabase(msg, title, company, trackingNumber, status);
            AddNotification(title);
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    /*private void SaveInDatabase(String msg, String title, String company, String trackingNumber, String status) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        // Adds the new message to the database
        if (company != null) {
            databaseHelper.insertData(date, title, msg, company, trackingNumber, status);
        } else {
            databaseHelper.insertData(date, title, msg, null, null, null);
        }
    }*/

    @SuppressLint("Wakelock")
    private void AddNotification(String msg) {
        WakeLock wakeLock = null;
        try {
            //Start Launch Activity
            String packageName = getPackageName();
            Intent resultIntent = new Intent(getPackageManager().getLaunchIntentForPackage(packageName));
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // Check for next activity
            String next_activity = "MainMenuActivity";

            resultIntent = new Intent(this, MainMenuActivity.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            //resultIntent.putExtras(intent.getBundleExtra("pushData"));
            // use System.currentTimeMillis() to have a unique ID for the pending intent
            PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), resultIntent, 0);
            // build notification
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setAutoCancel(true); // Make this notification automatically dismissed when the user touches it
            mBuilder.setContentTitle(getResources().getString(R.string.app_name));
            mBuilder.setContentText(msg);
            mBuilder.setContentIntent(pIntent);
            mBuilder.setTicker(msg);

            mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
            mBuilder.setLights(0xffffffff, 1000, 4000);
            Notification note = mBuilder.build();

            PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock((PowerManager.PARTIAL_WAKE_LOCK), "TAG");
            wakeLock.acquire();

            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify((int) SystemClock.currentThreadTimeMillis(), note);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (wakeLock != null && wakeLock.isHeld())
                wakeLock.release();
        }
    }
}
