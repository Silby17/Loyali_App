package com.silbytech.loyali.adapters;

import android.content.Context;
import android.content.SharedPreferences;
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
public class SubscriptionListAdapterNew extends BaseAdapter {
    private List<SubscriptionSerializable> subscriptionList;
    private String MEDIA_URL;
    public static final String PREFS = "prefs";
    private Context context;


    /****************************************************************************************
     * The constructor method
     * @param context - the application context
     * @param subscriptionList - list of all the customers subscriptions
     *****************************************************************************************/
    public SubscriptionListAdapterNew(Context context, List<SubscriptionSerializable> subscriptionList) {
        this.subscriptionList = subscriptionList;
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences(PREFS, 0);
        this.MEDIA_URL = preferences.getString("media_url", "");
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
        View v = View.inflate(context, R.layout.single_subscription_layout_new, null);
        TextView card1Header = (TextView) v.findViewById(R.id.reward1Title);
        TextView card1 = (TextView) v.findViewById(R.id.reward1Amount);

        TextView card2Header = (TextView) v.findViewById(R.id.reward2Title);
        TextView card2 = (TextView) v.findViewById(R.id.reward2Amount);
        ImageView storeLogo = (ImageView) v.findViewById(R.id.imgVendorLogo);

        String imageURL = MEDIA_URL + subscriptionList.get(position).getVendor().getLogoTitle();
        Picasso.with(context.getApplicationContext()).load(imageURL).into(storeLogo);

        if(subscriptionList.get(position).getCardInUse().size() == 2){
            String card1Max = Integer.toString(subscriptionList.get(position).getCardInUse().get(0).getCard().getMax());
            String card1Current = Integer.toString(subscriptionList.get(position).getCardInUse().get(0).getCurrent());
            String card1Count =  card1Current + "/" + card1Max;
            card1.setText(card1Count);
            card1Header.setText(subscriptionList.get(position).getCardInUse().get(0).getCard().getType());

            String card2Max = Integer.toString(subscriptionList.get(position).getCardInUse().get(1).getCard().getMax());
            String card2Current = Integer.toString(subscriptionList.get(position).getCardInUse().get(1).getCurrent());
            String card2Count =  card2Current + "/" + card2Max;
            card2.setText(card2Count);
            card2Header.setText(subscriptionList.get(position).getCardInUse().get(1).getCard().getType());

        } else if(subscriptionList.get(position).getCardInUse().size() == 1){
            String card1Max = Integer.toString(subscriptionList.get(position).getCardInUse().get(0).getCard().getMax());
            String card1Current = Integer.toString(subscriptionList.get(position).getCardInUse().get(0).getCurrent());
            String card1Count =  card1Current + "/" + card1Max;
            card1.setText(card1Count);
            card1Header.setText(subscriptionList.get(position).getCardInUse().get(0).getCard().getType());
        }
        //Sets the tag ID to the Subscription ID
        v.setTag(subscriptionList.get(position).getVendor().getId());
        return v;
    }
}
