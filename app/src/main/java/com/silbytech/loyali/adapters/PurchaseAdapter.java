package com.silbytech.loyali.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.silbytech.loyali.R;
import com.silbytech.loyali.entities.PurchaseSerializer;
import com.squareup.picasso.Picasso;

import java.util.List;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class PurchaseAdapter extends BaseAdapter {
    private String MEDIA_URL = "http://192.168.137.1:8000";
    private List<PurchaseSerializer> purchaseList;
    private Context context;

    public PurchaseAdapter(List<PurchaseSerializer> purchaseList, Context context) {
        this.purchaseList = purchaseList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return purchaseList.size();
    }

    @Override
    public Object getItem(int i) {
        return purchaseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.purchase_fragment, null);
        ImageView vendorLogo = (ImageView)v.findViewById(R.id.imgVendorLogo);
        TextView txtType = (TextView)v.findViewById(R.id.txtPurchaseType);
        TextView txtDate = (TextView)v.findViewById(R.id.txtPurchaseDate);

        String imageURL = MEDIA_URL + purchaseList.get(i).getVendor().getLogoTitle();
        Picasso.with(context.getApplicationContext()).load(imageURL).into(vendorLogo);

        txtType.setText(purchaseList.get(i).getType());
        txtDate.setText(purchaseList.get(i).getDate());
        RelativeLayout frag = (RelativeLayout)v.findViewById(R.id.purchaseFrag);

        if(purchaseList.get(i).getType().equals("Coffee")){
            Resources res = context.getResources(); //resource handle
            Drawable drawable = res.getDrawable(R.drawable.lighter_filter_coffee); //new Image that was added to the res folder
            frag.setBackground(drawable);
        }
        else if(purchaseList.get(i).getType().equals("Pastry")){
            Resources res = context.getResources(); //resource handle
            Drawable drawable = res.getDrawable(R.drawable.light_pastry); //new Image that was added to the res folder
            frag.setBackground(drawable);
        }
        return v;
    }
}
