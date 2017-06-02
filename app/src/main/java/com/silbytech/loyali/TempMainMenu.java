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
    Button btnClearData;
    Button btnQR;
    Button btnNEWQR;
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_menu);

        preferences = getSharedPreferences(PREFS, 0);

        btnSplash = (Button)findViewById(R.id.TMPSplashbtn);
        btnViewPager = (Button)findViewById(R.id.TMPViewPager);
        btnInfoTesting = (Button)findViewById(R.id.tmpINFOTesting);
        btnClearData = (Button)findViewById(R.id.tmpClearData);

        String customer = preferences.getString("customer_id", "");


        btnClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.getInstance(). clearApplicationData();
                preferences.edit().clear().commit();
                String test = preferences.getString("customer_id", "");
                System.out.println("Done");

            }
        });

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
                Intent i = new Intent(TempMainMenu.this, RedeemConfirmationActivity.class);
                TempMainMenu.this.startActivity(i);

            }
        });

    }
}
