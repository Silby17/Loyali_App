package com.silbytech.loyali;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class LoginActivity extends Activity {
    public static final String PREFS = "prefs";
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        android.app.FragmentManager fragMan = getFragmentManager();
        FragmentTransaction ft = fragMan.beginTransaction();

        FacebookLoginFragment facebookLoginFragment = new FacebookLoginFragment();
        ft.add(R.id.FB_login_frag, facebookLoginFragment);
        ft.commit();


        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(i);
            }
        });
    }
}
