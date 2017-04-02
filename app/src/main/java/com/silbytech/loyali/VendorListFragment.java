package com.silbytech.loyali;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.silbytech.loyali.adapters.VendorListAdapter;
import com.silbytech.loyali.entities.VendorSerializable;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class VendorListFragment extends android.support.v4.app.Fragment {
    //Deceleration of Variables
    private ListView lvVendors;
    private VendorListAdapter vendorListAdapter;
    private List<VendorSerializable> vendorList;

    public VendorListFragment() {}

    public static VendorListFragment newInstance(){
        VendorListFragment fragment = new VendorListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        (new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... params) {
                final Communicator communicator = new Communicator();
                communicator.getVendorList(new Callback<List<VendorSerializable>>() {
                    @Override
                    public void success(List<VendorSerializable> vendorSerializable, Response response) {
                        System.out.println("Done");
                        lvVendors = (ListView)getView().findViewById(R.id.lstViewVendors);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vendor_listview, container, false);
    }
}
