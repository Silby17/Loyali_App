package com.silbytech.loyali;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class RegisterActivity extends AppCompatActivity {
    private String TAG = "RegisterActivity";
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private LoginButton registerButton;
    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.register_layout);
        btnRegister = (Button)findViewById(R.id.btnRegisterNewUser);
        callbackManager = CallbackManager.Factory.create();
        registerButton = (LoginButton)findViewById(R.id.fb_register_btn);
        registerButton.setReadPermissions("public_profile");

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if(currentProfile != null){
                    System.out.println("User changed photo");
                    String facebookID = currentProfile.getId();
                    String firstName = currentProfile.getFirstName();
                    String surname = currentProfile.getLastName();
                    String fbMail = facebookID + "@loyali.com";
                    System.out.println("Gotten the names");

                    (new AsyncTask<String, Void, Void>(){
                        @Override
                        protected Void doInBackground(String... params) {
                            Communicator communicator = new Communicator();
                            communicator.postNewMobileUserAPI(params[0], params[1], params[2],
                                    params[3], params[4], params[5], params[6],
                                    new Callback<RegisterResponse>() {
                                        @Override
                                        public void success(RegisterResponse registerResponse, Response response) {
                                            System.out.println("Success with registration");
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.d(TAG, "Registration with server Failed");

                                        }
                                    });
                            return null;
                        }
                    }).execute(firstName, surname, fbMail, fbMail, facebookID, null);
                }
            }
        };

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**Extract the inputs from the Edit Texts**/
                EditText firstName = (EditText) findViewById(R.id.edtFirstName);
                EditText lastName = (EditText) findViewById(R.id.edtLastName);
                EditText mail = (EditText) findViewById(R.id.edtEmail);
                EditText pass = (EditText) findViewById(R.id.edtPassword);
                EditText verifyPass = (EditText) findViewById(R.id.edtVerifyPassword);

                /**Convert to strings and check that all the parameters have been entered**/
                String tempFB = "";
                String tempPush = "temp Push Key";
                String fName = firstName.getText().toString();
                String surname = lastName.getText().toString();
                final String email = mail.getText().toString();
                final String password = pass.getText().toString();
                String verPassword = verifyPass.getText().toString();
                if (fName.equals("") || surname.equals("") || password.equals("") ||
                        verPassword.equals("") || email.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            R.string.errorMissingInfo, Toast.LENGTH_SHORT).show();
                } else if (!verPassword.equals(password)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.passMisMatch, Toast.LENGTH_SHORT).show();
                } else {
                    (new AsyncTask<String, Void, Void>() {
                        @Override
                        protected Void doInBackground(String... params) {
                            Communicator communicator = new Communicator();
                            communicator.postNewMobileUserAPI(params[0], params[1], params[2],
                                    params[3], params[4], params[5], params[6],
                                    new Callback<RegisterResponse>() {
                                        @Override
                                        public void success(RegisterResponse registerResponse, Response response) {
                                            if(response.getStatus() == 200){
                                                //Gets the Shared Preferences
                                                String fullName = registerResponse.getApiUser().getFirstName() + " " + registerResponse.getApiUser().getLastName();
                                                preferences = getSharedPreferences(PREFS, 0);
                                                preferences.edit().putBoolean("isFacebookUser", false).apply();
                                                //Adds the email, customer ID and login boolean to Prefs
                                                preferences.edit().putInt("userid", registerResponse.getApiUser().getCustomer_id()).apply();
                                                SplashActivity.pubnubController.RegisterUserChannel();
                                                preferences.edit().putString("username", email).apply();
                                                preferences.edit().putString("fullName", fullName).apply();
                                                preferences.edit().putString("customer_id", Integer.toString(registerResponse.getApiUser().getCustomer_id())).apply();
                                                preferences.edit().putBoolean("logged-in", true).apply();

                                                Intent i = new Intent(RegisterActivity.this, MainMenuActivity.class);
                                                RegisterActivity.this.startActivity(i);
                                                finish();
                                            }
                                        }
                                        @Override
                                        public void failure(RetrofitError error) {
                                            Toast.makeText(getApplicationContext(),
                                                    R.string.connectionError, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );
                            return null;
                        }

                    }).execute(fName, surname, email, email, password, tempFB, tempPush);
                }
            }
        });

        registerButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, loginResult.toString());
                String userID = AccessToken.getCurrentAccessToken().getUserId();
                Log.d(TAG, "User ID is: " + userID);
            }
            @Override
            public void onCancel() {
                Log.d(TAG, "User Cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        profileTracker.stopTracking();
    }
}
