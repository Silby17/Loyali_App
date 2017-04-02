package com.silbytech.loyali.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.silbytech.loyali.R;
import com.silbytech.loyali.entities.VendorSerializable;
import com.squareup.picasso.Picasso;
import java.util.List;


/************************************
 * Created by Yosef Silberhaft
 ************************************/

public class VendorListAdapter extends BaseAdapter {
    //Deceleration of Variables
    private Context context;
    private List<VendorSerializable> vendorsList;
    private String MEDIA_URL = "http://192.168.1.13:8000";

    public VendorListAdapter(Context context, List<VendorSerializable> vendorsList) {
        this.context = context;
        this.vendorsList = vendorsList;
    }

    @Override
    public int getCount() {
        return vendorsList.size();
    }

    @Override
    public Object getItem(int position) {
        return vendorsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.vendgor_fragment, null);
        TextView storeName = (TextView)v.findViewById(R.id.txtViewVendorName);
        TextView storeLocation = (TextView)v.findViewById(R.id.txtViewVendorLocation);
        TextView storePhone = (TextView)v.findViewById(R.id.txtViewPhone);
        TextView storeType = (TextView)v.findViewById(R.id.txtStoreType);
        ImageView storeLogo = (ImageView)v.findViewById(R.id.imgVendorLogo);

        String imageURL = MEDIA_URL + vendorsList.get(position).getLogoTitle();
        System.out.println(imageURL);
        Picasso.with(context.getApplicationContext()).load(imageURL).into(storeLogo);
        storeName.setText(vendorsList.get(position).getStoreName());
        storeLocation.setText(vendorsList.get(position).getLocation());
        storePhone.setText(vendorsList.get(position).getPhone());
        storeType.setText(vendorsList.get(position).getStoreType());

        //Save Vendor id to tag
        v.setTag(vendorsList.get(position).getId());
        return v;
    }
}