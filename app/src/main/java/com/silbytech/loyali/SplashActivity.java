package com.silbytech.loyali;

import com.silbytech.loyali.controllers.PubnubController;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class SplashActivity extends AppCompatActivity {
    public static final String TAG = "SplashActivity";
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;

    public static PubnubController pubnubController = new PubnubController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**Show Window in full screen without status bar**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        /**First time in the Application**/
        preferences = getSharedPreferences(PREFS, 0);
        final Boolean loggedIn = preferences.contains("logged-in");
        Handler handler = new Handler();

        /**This will check if the user has already signed in to the application**/
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loggedIn) {
                    Toast.makeText(getApplicationContext(),
                            "Welcome man!",
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SplashActivity.this, MainMenuActivity.class);
                    SplashActivity.this.startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(i);
                    finish();
                }
            }
        }, 2000);
        pubnubController.Start(getApplicationContext());
    }
}
