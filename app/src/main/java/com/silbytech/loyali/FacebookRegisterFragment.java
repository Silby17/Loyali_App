package com.silbytech.loyali;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.silbytech.loyali.responses.RegisterResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class FacebookRegisterFragment extends Fragment {
    //Deceleration of variables
    private CallbackManager mCallBackManager;
    private String TAG = "FacebookRegisterFragment";
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;

    /*************************************************************************
     * This will register the login callback response from the Facebook login
     *************************************************************************/
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {

        private ProfileTracker mProfileTracker;
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            if(Profile.getCurrentProfile() == null){
                mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        Log.v("facebook - profile", currentProfile.getFirstName());
                        mProfileTracker.stopTracking();
                    }
                };
            }
            Profile profile = Profile.getCurrentProfile();
            String firstName = profile.getFirstName();
            String lastName = profile.getLastName();
            String userId = profile.getId();
            //These will be made up password and email for the facebook user.
            String fbPass = firstName + " " + lastName;
            String fbUserEmail = firstName + lastName + "@loyali.com";
            String tempPush = "null";

            (new AsyncTask<String, Void, Void>(){
                @Override
                protected Void doInBackground(String... params) {
                    Communicator communicator = new Communicator();
                    communicator.postNewMobileUserAPI(params[0], params[1], params[2], params[3], params[4],
                            params[5], params[6],
                            new Callback<RegisterResponse>() {
                                @Override
                                public void success(RegisterResponse registerResponse, Response response) {
                                    System.out.println("success");
                                    //Gets the Shared Preferences
                                    preferences = getActivity().getPreferences(0);
                                    preferences.edit().putBoolean("isFacebookUser", true).apply();
                                    //Adds the email, customer ID and login boolean to Prefs
                                    preferences.edit().putString("customer_id", Integer.toString(registerResponse.getApiUser().getCustomer_id())).apply();
                                    preferences.edit().putBoolean("logged-in", true).apply();
                                    Intent i = new Intent(getActivity(), MainMenuActivity.class);
                                    startActivity(i);
                                    getActivity().finish();
                                }
                                @Override
                                public void failure(RetrofitError error) {
                                    Toast.makeText(getApplicationContext(),
                                            R.string.connectionError, Toast.LENGTH_SHORT).show();
                                }
                            });
                    return null;
                }
            }).execute(firstName, lastName, fbUserEmail, userId, fbPass, userId, tempPush);
        }

        @Override
        public void onCancel() {}

        @Override
        public void onError(FacebookException error) {
            Toast.makeText(getApplicationContext(),
                    R.string.connectionError, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallBackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.facebook_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton)view.findViewById(R.id.FB_login_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallBackManager, mCallback);
        /*loginButton.registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("Facebook logged in");
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
            }

            @Override
            public void onCancel() {
                System.out.println("User cancelled");

            }

            @Override
            public void onError(FacebookException error) {

            }
        });*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }
}