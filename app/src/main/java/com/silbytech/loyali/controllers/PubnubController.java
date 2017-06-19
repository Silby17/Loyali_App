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
    //Define PubNub Keys for Push Notifications
    public static final String PREFS = "SP_Pubnub";
    private String PUB_KEY = "pub-c-87b891e4-b074-4574-82ef-a46032ce6818";
    private String SUBS_KEY = "sub-c-884705e8-46db-11e7-b847-0619f8945a4f";
    private String SENDER_ID = "591905335934"; // GCM Project Number (Not Project ID)

    int PLAY_SERVICES_RESOLUTION_REQUEST = 1122;
    private String PROPERTY_REG_ID = "DevRegIDKey";
    private GoogleCloudMessaging gcm;
    private String regId = "";
    private Context context;
    private Pubnub mPubnub;
    private static String APP_CHANNEL = "AppBroadcast";
    private static String USER_CHANNEL_PREFIX = "USER_";

    /********************************************************************************
     * Starts the PubNub functionality using the defined Keys
     * @param _context - passes the current context
     ********************************************************************************/
    public void Start(Context _context) {
        context = _context;
        mPubnub = new Pubnub(PUB_KEY, SUBS_KEY);
        RegisterAppChannel();
    }


    /**********************************************************************************
     * PubNub register device using GCM
     **********************************************************************************/
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

    /**********************************************************************************
     * Gets the users registration ID from the preferences
     * @return - the registration ID
     * @throws Exception - if the string isn't found
     *********************************************************************************/
    private String getRegistrationId() throws Exception {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, 0);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            return "";
        }
        //Returns the Registration ID
        return registrationId;
    }

    /********************************************************************************
     * Adds the Registration ID of the User in shared Preferences.
     * @param regId - the registration ID
     * @throws Exception - Exception getting Shared Preferences
     *********************************************************************************/
    private void storeRegistrationId(String regId) throws Exception {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.apply();
    }

    /***********************************************************************************
     * Registers the device in the Background while the rest of the app is starting up
     ***********************************************************************************/
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


    /********************************************************************************
     * Gets the User ID from Shared Preferences
     * @return - the UsersID
     *********************************************************************************/
    private int getUserId(){
        SharedPreferences prefs = context.getSharedPreferences(SplashActivity.PREFS, 0);
        int userID = prefs.getInt("userid", 0);
        return userID;
    }


    /********************************************************************************
     * Register for the User Channel using the users ID
     ********************************************************************************/
    public void RegisterUserChannel() {
        final int userId  = getUserId();
        if(userId > 0) {
            register();
            if (!regId.isEmpty()) {
                final String CHANNEL = USER_CHANNEL_PREFIX + userId;
                mPubnub.enablePushNotificationsOnChannel(CHANNEL, regId, new Callback() {
                    @Override
                    public void successCallback(String channel, Object message) {
                        System.out.println("Success - Registering on Channel: " + CHANNEL);
                    }
                    @Override
                    public void errorCallback(String channel, PubnubError error) {
                        System.out.println("Failed to register on Channel: " + CHANNEL);
                    }
                });
            }
        }
    }


    /********************************************************************************
     * Registers the User for Loyali Message Broadcast
     ********************************************************************************/
    public void RegisterAppChannel() {
        register();
        if (!regId.isEmpty()) {
            mPubnub.enablePushNotificationsOnChannel(APP_CHANNEL, regId, new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    System.out.println("Successfully registered on Channel: " + APP_CHANNEL);
                }
                @Override
                public void errorCallback(String channel, PubnubError error) {
                    System.out.println("Failed - Registration on channel: " + APP_CHANNEL);
                }
            });
        }
    }


    /********************************************************************************
     * Subscribes the User to the desired Vendors Channel
     * @param vendorID - the Vendor ID to subscribe to
     ********************************************************************************/
    public void RegisterVendorChannel(int vendorID){
        //Creates the Vendor Channel
        final String vendorChannel = "Broadcast_Vendor_" + Integer.toString(vendorID);

        if(!regId.isEmpty()){
            mPubnub.enablePushNotificationsOnChannel(vendorChannel, regId, new Callback() {
                @Override
                public void successCallback(String s, Object o) {
                    System.out.println("PUBNUB - User Registered with: " + vendorChannel);
                }
                @Override
                public void errorCallback(String s, PubnubError pubnubError) {
                    System.out.println("PUBNUB - User FAILED to Registered with: " + vendorChannel);
                }
            });
        }
    }


    /********************************************************************************
     * UnSubscribe from a specific Vendor Channel
     * @param vendorID - Vendor ID to UnSubscribe from
     *********************************************************************************/
    public void DeRegisterVendorChannel(int vendorID){
        //Creates the Vendor Channel
        final String vendorChannel = "Broadcast_Vendor_" + Integer.toString(vendorID);
        if(!regId.isEmpty()){
            mPubnub.disablePushNotificationsOnChannel(vendorChannel, regId, new Callback() {
                @Override
                public void successCallback(String s, Object o) {
                    System.out.println("PUBNUB - User Registered with: " + vendorChannel);
                }
                @Override
                public void errorCallback(String s, PubnubError pubnubError) {
                    System.out.println("failed");
                }
            });
        }
    }
}