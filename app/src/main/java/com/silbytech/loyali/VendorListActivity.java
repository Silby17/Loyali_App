package com.silbytech.loyali;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.silbytech.loyali.adapters.VendorListAdapter;
import com.silbytech.loyali.entities.VendorSerializable;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class VendorListActivity extends Activity {
    /**Deceleration of variables**/
    private ListView lvVendors;
    private VendorListAdapter vendorListAdapter;
    private List<VendorSerializable> vendorList;
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_listview);

        (new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... params) {
                final Communicator communicator = new Communicator();
                communicator.getVendorList(new Callback<List<VendorSerializable>>() {
                    @Override
                    public void success(List<VendorSerializable> vendorSerializable, Response response) {
                        System.out.println("Done");
                        lvVendors = (ListView)findViewById(R.id.lstViewVendors);
                        vendorList = vendorSerializable;

                        //Init Adapter
                        vendorListAdapter = new VendorListAdapter(getApplicationContext(), vendorList);
                        lvVendors.setAdapter(vendorListAdapter);


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println("Failed man sorry ");

                    }
                });


                return null;
            }
        }).execute();
    }
}
