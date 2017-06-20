package com.silbytech.loyali;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class UserProfileActivity extends AppCompatActivity {
    private String TAG = "UserProfileActivity";
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        preferences = getSharedPreferences(PREFS, 0);
        String username = preferences.getString("username", "");
        String fullName = preferences.getString("fullName", "");
        String id = preferences.getString("customer_id", "");

        TextView tvFullName = (TextView)findViewById(R.id.txtProfileFullName);
        TextView tvUsername = (TextView)findViewById(R.id.txtProfileUsername);
        TextView tvCustomerID = (TextView)findViewById(R.id.txtCustomerID);

        tvFullName.setText(fullName);
        tvUsername.setText(username);
        tvCustomerID.setText(id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


    public void logout(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Logout");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        MyApplication.getInstance().clearApplicationData();
                        SharedPreferences prefs = getSharedPreferences(PREFS, 0);
                        prefs.edit().clear().apply();
                        startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        // Create Alert Dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Show the Dialog Box
        alertDialog.show();
    }
}