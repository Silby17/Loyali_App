package com.silbytech.loyali.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private ArrayList<String> mFragmentListTitles = new ArrayList<>();


    /****************************************************************************************
     * Fragment Page Adapter constructor
     * @param fm - the Fragment manager
     *****************************************************************************************/
    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentListTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentListTitles.get(position);
    }
}
