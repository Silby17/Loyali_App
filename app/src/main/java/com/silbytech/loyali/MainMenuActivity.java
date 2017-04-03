package com.silbytech.loyali;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.silbytech.loyali.adapters.ViewPagerAdapter;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class MainMenuActivity extends AppCompatActivity {
    public static final String PREFS = "prefs";
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_fragments);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        // Creates a new View Pager Adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

       /* // Adds Rewards Page to ViewPager
        adapter.addFragment(null, "My Rewards");
*/
        // Adds Vendor List Page to ViewPager
        adapter.addFragment(VendorListFragment.newInstance(), "Stores");

        // Adds SubscriptionSerializable Page to ViewPager
        adapter.addFragment(SubscriptionListFragment.newInstance(), "My Stores");

        viewPager.setAdapter(adapter);

        //Sets the fragment at index 1 to be the opening fragment
        viewPager.setCurrentItem(0);
    }
}
