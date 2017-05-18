package com.silbytech.loyali.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.silbytech.loyali.R;
import com.silbytech.loyali.entities.SubscriptionSerializable;
import com.squareup.picasso.Picasso;
import java.util.List;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class SubscriptionListAdapter extends BaseAdapter {
    private List<SubscriptionSerializable> subscriptionList;
    private String MEDIA_URL = "http://192.168.137.1:8000";
    private Context context;

    public SubscriptionListAdapter(Context context, List<SubscriptionSerializable> subscriptionList) {
        this.context = context;
        this.subscriptionList = subscriptionList;
    }


    @Override
    public int getCount() {
        return subscriptionList.size();
    }


    @Override
    public Object getItem(int position) {
        return subscriptionList.get((position));
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.single_subscription_fragment, null);
        TextView storeName = (TextView) v.findViewById(R.id.txtViewVendorName);
        TextView storeLocation = (TextView) v.findViewById(R.id.txtViewVendorLocation);
        TextView storePhone = (TextView) v.findViewById(R.id.txtViewPhone);
        TextView storeType = (TextView) v.findViewById(R.id.txtStoreType);
        TextView card1 = (TextView) v.findViewById(R.id.txtCard1Count);
        TextView card1Header = (TextView) v.findViewById(R.id.txtCard1Header);

        TextView card2 = (TextView) v.findViewById(R.id.txtCard2Count);
        TextView card2Header = (TextView) v.findViewById(R.id.txtCard2Header);
        ImageView storeLogo = (ImageView) v.findViewById(R.id.imgVendorLogo);

        String imageURL = MEDIA_URL + subscriptionList.get(position).getVendor().getLogoTitle();
        Picasso.with(context.getApplicationContext()).load(imageURL).into(storeLogo);
        storeName.setText(subscriptionList.get(position).getVendor().getStoreName());
        storeLocation.setText(subscriptionList.get(position).getVendor().getLocation());
        storePhone.setText(subscriptionList.get(position).getVendor().getPhone());
        storeType.setText(subscriptionList.get(position).getVendor().getStoreType());

        if(subscriptionList.get(position).getCardInUse().size() == 2){
            String card1Max = Integer.toString(subscriptionList.get(position).getCardInUse().get(0).getCard().getMax());
            String card1Current = Integer.toString(subscriptionList.get(position).getCardInUse().get(0).getCurrent());
            String card1Count =  card1Current + "/" + card1Max;
            card1.setText(card1Count);
            card1Header.setText(subscriptionList.get(position).getCardInUse().get(0).getCard().getDescription());

            String card2Max = Integer.toString(subscriptionList.get(position).getCardInUse().get(1).getCard().getMax());
            String card2Current = Integer.toString(subscriptionList.get(position).getCardInUse().get(1).getCurrent());
            String card2Count =  card2Current + "/" + card2Max;
            card2.setText(card2Count);
            card2Header.setText(subscriptionList.get(position).getCardInUse().get(1).getCard().getDescription());

        } else if(subscriptionList.get(position).getCardInUse().size() == 1){
            String card1Max = Integer.toString(subscriptionList.get(position).getCardInUse().get(0).getCard().getMax());
            String card1Current = Integer.toString(subscriptionList.get(position).getCardInUse().get(0).getCurrent());
            String card1Count =  card1Current + "/" + card1Max;
            card1.setText(card1Count);
            card1Header.setText(subscriptionList.get(position).getCardInUse().get(0).getCard().getDescription());
        }
        v.setTag(subscriptionList.get(position).getVendor().getId());
        return v;
    }
}