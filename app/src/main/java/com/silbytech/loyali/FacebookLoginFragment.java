package com.silbytech.loyali;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class FacebookLoginFragment extends Fragment {
    //Deceleration of variables
    private CallbackManager mCallBackManager;
    private String TAG = "Facebook Login Frgament";
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;

    /*************************************************************************
     * This will register the login callback response from the Facebook login
     *************************************************************************/
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
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
                    communicator.postNewMobileUserAPI(params[0], params[1], params[2],
                            params[3], params[4], params[5], params[6],
                            new Callback<RegisterResponse>() {
                                @Override
                                public void success(RegisterResponse registerResponse, Response response) {
                                    if(response.getStatus() == 200){
                                        System.out.println("Working");
                                    }
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
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallBackManager, mCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }
}
