package com.silbytech.loyali;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class TempMainMenu extends Activity {
    Button vendors;
    Button btnSplash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_menu);

        vendors = (Button)findViewById(R.id.TMPbtnVendorList);
        btnSplash = (Button)findViewById(R.id.TMPSplashbtn);

        vendors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TempMainMenu.this, VendorListActivity.class);
                TempMainMenu.this.startActivity(i);
            }
        });

        btnSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TempMainMenu.this, SplashActivity.class);
                TempMainMenu.this.startActivity(i);

            }
        });
    }
}
