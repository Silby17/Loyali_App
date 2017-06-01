package com.silbytech.loyali;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class UserProfileActivity extends AppCompatActivity {
    private String TAG = "UserProfileActivity";
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        preferences = getSharedPreferences(PREFS, 0);
        String username = preferences.getString("username", "");
        String fullName = preferences.getString("fullName", "");
        String id = preferences.getString("customer_id", "");

        TextView tvFullName = (TextView)findViewById(R.id.txtProfileFullName);
        TextView tvUsername = (TextView)findViewById(R.id.txtProfileUsername);

        tvFullName.setText(fullName);
        tvUsername.setText(username);

    }
}
