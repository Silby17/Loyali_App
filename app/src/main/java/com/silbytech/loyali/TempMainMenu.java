package com.silbytech.loyali;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.widget.Button;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class TempMainMenu extends Activity {
    Button vendors;
    Button btnSplash;
    Button btnViewPager;
    Button btnInfoTesting;
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_menu);

        preferences = getSharedPreferences(PREFS, 0);
        preferences.edit().putString("customer_id", "8").apply();


        btnSplash = (Button)findViewById(R.id.TMPSplashbtn);
        btnViewPager = (Button)findViewById(R.id.TMPViewPager);
        btnInfoTesting = (Button)findViewById(R.id.tmpINFOTesting);


        btnSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TempMainMenu.this, SplashActivity.class);
                TempMainMenu.this.startActivity(i);
            }
        });


        btnViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TempMainMenu.this, MainMenuActivity.class);
                TempMainMenu.this.startActivity(i);
            }
        });

        btnInfoTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TempMainMenu.this, TesterActivity.class);
                TempMainMenu.this.startActivity(i);
            }
        });
    }
}
