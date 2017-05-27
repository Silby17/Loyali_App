package com.silbytech.loyali.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.silbytech.loyali.R;
import com.silbytech.loyali.entities.CustomerRewardsListSerializable;
import com.squareup.picasso.Picasso;

import java.util.List;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class RewardsAdapter extends BaseAdapter {
    private List<CustomerRewardsListSerializable> rewardsList;
    private String MEDIA_URL = "http://192.168.137.1:8000";
    private Context context;

    public RewardsAdapter(List<CustomerRewardsListSerializable> rewardsList, Context context) {
        this.rewardsList = rewardsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return rewardsList.size();
    }

    @Override
    public Object getItem(int i) {
        return rewardsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.rewards_laoyut_fragment, null);
        TextView storeName = (TextView)v.findViewById(R.id.txtViewVendorName);
        TextView location = (TextView)v.findViewById(R.id.txtViewVendorLocation);
        TextView phone = (TextView)v.findViewById(R.id.txtVendorPhone);
        TextView type = (TextView)v.findViewById(R.id.txtVendorType);

        TextView reward1Title = (TextView)v.findViewById(R.id.reward1Title);
        TextView reward2Title = (TextView)v.findViewById(R.id.reward2Title);

        TextView reward1Amount = (TextView)v.findViewById(R.id.reward1Amount);
        TextView reward2Amount = (TextView)v.findViewById(R.id.reward2Amount);



        ImageView storeLogo = (ImageView) v.findViewById(R.id.imgVendorLogo);
        String imageURL = MEDIA_URL + rewardsList.get(i).getLogoTitle();
        Picasso.with(context.getApplicationContext()).load(imageURL).into(storeLogo);

        storeName.setText(rewardsList.get(i).getStoreName());
        location.setText(rewardsList.get(i).getLocation());
        phone.setText(rewardsList.get(i).getPhone());
        type.setText(rewardsList.get(i).getStoreType());

        if(rewardsList.get(i).getRewardsList().size() != 0){
            reward1Title.setText(rewardsList.get(i).getRewardsList().get(0).getType());
            reward1Amount.setText(Integer.toString(rewardsList.get(i).getRewardsList().get(0).getAmount()));
        }
        if(rewardsList.get(i).getRewardsList().size() == 2){
            reward2Title.setText(rewardsList.get(i).getRewardsList().get(1).getType());
            reward2Amount.setText(Integer.toString(rewardsList.get(i).getRewardsList().get(1).getAmount()));
        }
        v.getTag(rewardsList.get(i).getId());
        return v;
    }
}
