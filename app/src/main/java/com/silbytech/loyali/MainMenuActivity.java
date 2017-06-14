package com.silbytech.loyali;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.silbytech.loyali.adapters.ViewPagerAdapter;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class MainMenuActivity extends AppCompatActivity {
    public static final String PREFS = "prefs";
    Context context = this;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_fragments);
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        // Creates a new View Pager Adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(RewardsListFragment.newInstance(), "Rewards");

        // Adds Vendor List Page to ViewPager
        adapter.addFragment(VendorListFragment.newInstance(), "Stores");

        // Adds SubscriptionSerializable Page to ViewPager
        adapter.addFragment(SubscriptionListFragment.newInstance(), "My Stores");

        viewPager.setAdapter(adapter);

        //Sets the fragment at index 1 to be the opening fragment
        viewPager.setCurrentItem(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.itemProfile:
                Intent i = new Intent(MainMenuActivity.this, UserProfileActivity.class);
                MainMenuActivity.this.startActivity(i);
                return true;
            case R.id.itemPurchaseHistory:
                Intent pIntent = new Intent(MainMenuActivity.this, PurchasesActivity.class);
                MainMenuActivity.this.startActivity(pIntent);
                return true;
            case R.id.itemLogout:
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
                                startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}