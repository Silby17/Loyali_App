package com.silbytech.loyali.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.silbytech.loyali.SplashActivity;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class PubnubController {

    public static final String PREFS = "SP_Pubnub";
    private String PUB_KEY = "pub-c-87b891e4-b074-4574-82ef-a46032ce6818";
    private String SUBS_KEY = "sub-c-884705e8-46db-11e7-b847-0619f8945a4f";
    String SENDER_ID = "591905335934"; // GCM Project Number (Not Project ID)

    int PLAY_SERVICES_RESOLUTION_REQUEST = 1122;
    String PROPERTY_REG_ID = "DevRegIDKey";
    GoogleCloudMessaging gcm;
    String regId = "";
    Context context;

    private Pubnub mPubnub;
    private static String APP_CHANNEL = "AppBroadcast";
    private static String USER_CHANNEL_PREFIX = "USER_";

    public void Start(Context _context) {
        context = _context;

        mPubnub = new Pubnub(PUB_KEY, SUBS_KEY);
        RegisterAppChannel();
    }

    private void register() {
        gcm = GoogleCloudMessaging.getInstance(context);
        try {
            regId = getRegistrationId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (regId.isEmpty()) {
            registerInBackground();
        } else {
            // Registration ID already exists: " + regId
        }
    }

    private String getRegistrationId() throws Exception {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, 0);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            return "";
        }

        return registrationId;
    }

    private void storeRegistrationId(String regId) throws Exception {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.apply();
    }

    private void registerInBackground() {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected String doInBackground(Object... params) {
                String msg;
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID: " + regId;

                    storeRegistrationId(regId);

                    RegisterAppChannel();

                    RegisterUserChannel();

                } catch (Exception ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }
        }.execute(null, null, null);
    }

    private int getUserId(){
        SharedPreferences prefs = context.getSharedPreferences(SplashActivity.PREFS, 0);
        int userid = prefs.getInt("userid", 0);
        return userid;
    }

    public void RegisterUserChannel() {
        int userId  = getUserId();

        if(userId > 0) {
            register();

            if (!regId.isEmpty()) {
                final String CHANNEL = USER_CHANNEL_PREFIX + userId;
                mPubnub.enablePushNotificationsOnChannel(CHANNEL, regId, new Callback() {
                    @Override
                    public void successCallback(String channel, Object message) {
                        System.out.println("Success");

                    }

                    @Override
                    public void errorCallback(String channel, PubnubError error) {
                        System.out.println("Failed");
                    }
                });
            }
        }
    }

    public void RegisterAppChannel() {
        register();

        if (!regId.isEmpty()) {
            mPubnub.enablePushNotificationsOnChannel(APP_CHANNEL, regId, new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    System.out.println("Successful");
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    System.out.println("Failed");
                }
            });
        }
    }
}
