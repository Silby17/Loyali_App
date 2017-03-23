package com.silbytech.loyali;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class RegisterActivity extends AppCompatActivity {
    private String TAG = "RegisterActivity";
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        android.app.FragmentManager fragMan = getFragmentManager();
        FragmentTransaction ft = fragMan.beginTransaction();
        FacebookRegisterFragment facebookRegisterFragment = new FacebookRegisterFragment();
        ft.add(R.id.frag1, facebookRegisterFragment);
        ft.commit();
    }
}
