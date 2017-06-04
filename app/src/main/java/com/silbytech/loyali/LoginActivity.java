package com.silbytech.loyali;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.silbytech.loyali.responses.RegisterResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class LoginActivity extends Activity {
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_layout);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = ((EditText)findViewById(R.id.edtLoginEmail)).getText().toString();
                final String password = ((EditText)findViewById(R.id.edtLoginPassword)).getText().toString();

                if(username.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(),
                            R.string.errorMissingInfo, Toast.LENGTH_SHORT).show();
                }
                else{
                    (new AsyncTask<String, Void, Void>(){
                        @Override
                        protected Void doInBackground(String... params) {
                            Communicator communicator = new Communicator();
                            communicator.userLogin(params[0], params[1], new Callback<RegisterResponse>() {
                                @Override
                                public void success(RegisterResponse resultResponse, Response response) {
                                    String fullName = resultResponse.getApiUser().getFirstName() + " " + resultResponse.getApiUser().getLastName();
                                    preferences = getSharedPreferences(PREFS, 0);
                                    preferences.edit().putBoolean("isFacebookUser", false).apply();
                                    //Adds the email, customer ID and login boolean to Prefs
                                    preferences.edit().putInt("userid", resultResponse.getApiUser().getCustomer_id()).apply();
                                    SplashActivity.pubnubController.RegisterUserChannel();
                                    preferences.edit().putString("username", username).apply();
                                    preferences.edit().putString("pass", password).apply();
                                    preferences.edit().putString("customer_id", Integer.toString(resultResponse.getApiUser().getCustomer_id())).apply();
                                    preferences.edit().putString("fullName", fullName).apply();
                                    preferences.edit().putBoolean("logged-in", true).apply();
                                    Intent i = new Intent(LoginActivity.this, MainMenuActivity.class);
                                    LoginActivity.this.startActivity(i);
                                    finish();
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    if(error.getKind().name().equals("NETWORK")){
                                        Toast.makeText(getApplicationContext(),
                                                R.string.networkError, Toast.LENGTH_SHORT).show();
                                    }
                                    else if(error.getResponse().getStatus() == 409){
                                        Toast.makeText(getApplicationContext(),
                                                R.string.user_not_exist, Toast.LENGTH_SHORT).show();
                                    }
                                    }
                            });
                            return null;
                        }
                    }).execute(username, password);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(i);
            }
        });
    }
}