package com.silbytech.loyali;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silbytech.loyali.entities.CustomerRewardsListSerializable;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class SingleRewardActivity extends AppCompatActivity {
    private String TAG = "SingleVendorSubscription";
    private String MEDIA_URL = "http://192.168.137.1:8000";
    public static final String PREFS = "prefs";
    public int buttonClicked = 0;
    SharedPreferences preferences;
    private int reward1ID;
    private int reward2ID;
    private String customer_id;
    private String vendor_id;
    ImageView logo;
    TextView txtTitle;
    TextView txtType;
    TextView txtLocation;
    TextView txtPhone;
    TextView reward1Title;
    TextView reward2Title;
    TextView reward1Amount;
    TextView reward2Amount;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_reward_layout);

        logo = (ImageView)findViewById(R.id.imgVendorLogo);
        txtTitle = (TextView)findViewById(R.id.txtVendorTitle);
        txtType = (TextView)findViewById(R.id.txtVendorType);
        txtLocation = (TextView)findViewById(R.id.txtVendorLocation);
        txtPhone = (TextView)findViewById(R.id.txtVendorPhone);

        reward1Title = (TextView)findViewById(R.id.reward1Title);
        reward2Title = (TextView)findViewById(R.id.reward2Title);
        reward1Amount = (TextView)findViewById(R.id.reward1Amount);
        reward2Amount = (TextView)findViewById(R.id.reward2Amount);
        preferences = getApplicationContext().getSharedPreferences(PREFS, 0);
        this.customer_id = preferences.getString("customer_id", "");
        this.vendor_id = getIntent().getStringExtra("vendor_id");


        (new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... strings) {
                Communicator communicator = new Communicator();
                communicator.getRewardsByVendor(strings[0], strings[1],
                        new Callback<List<CustomerRewardsListSerializable>>() {
                            @Override
                            public void success(List<CustomerRewardsListSerializable> customerRewardsListSerializables, Response response) {
                                String imageURL = MEDIA_URL + customerRewardsListSerializables.get(0).getLogoTitle();
                                Picasso.with(getApplicationContext()).load(imageURL).into(logo);
                                txtTitle.setText(customerRewardsListSerializables.get(0).getStoreName());
                                txtType.setText(customerRewardsListSerializables.get(0).getStoreType());
                                txtPhone.setText(customerRewardsListSerializables.get(0).getPhone());
                                txtLocation.setText(customerRewardsListSerializables.get(0).getLocation());

                                if(customerRewardsListSerializables.get(0).getRewardsList() != null){
                                    reward1ID = customerRewardsListSerializables.get(0).getRewardsList().get(0).getId();
                                    reward1Title.setText(customerRewardsListSerializables.get(0)
                                            .getRewardsList().get(0).getType());
                                    reward1Amount.setText(Integer.toString(customerRewardsListSerializables.get(0)
                                            .getRewardsList().get(0).getAmount()));
                                }
                                if(customerRewardsListSerializables.get(0).getRewardsList().size() == 2){
                                    reward2ID = customerRewardsListSerializables.get(0).getRewardsList().get(1).getId();
                                    reward2Title.setText(customerRewardsListSerializables.get(0)
                                            .getRewardsList().get(1).getType());
                                    reward2Amount.setText(Integer.toString(customerRewardsListSerializables.get(0)
                                            .getRewardsList().get(1).getAmount()));
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                System.out.println("Failed");

                            }
                        });

                return null;
            }
        }).execute(customer_id, vendor_id);
    }

    /*************************************************************
     * This method will override the onBackPressed so that after
     * the user has punched his card all new updated info
     * is loaded and seen in the app
     *************************************************************/
    @Override
    public void onBackPressed() {
        Intent i = new Intent(SingleRewardActivity.this, MainMenuActivity.class);
        this.startActivity(i);
        this.finish();
    }


    /**********************************************************************************
     * Method that wil handle the onClick of the First (Left) reward on the screen
     * @param view - that was clicked
     **********************************************************************************/
    public void reward1OnClick(View view){
        buttonClicked = 1;
        Toast.makeText(getApplicationContext(), "First Reward clicked ID: = " + reward1ID, Toast.LENGTH_SHORT).show();

    }

    /**********************************************************************************
     * Method that wil handle the onClick of the Second (Right) reward on the screen
     * @param view - that was clicked
     **********************************************************************************/
    public void reward2OnClick(View view){
        buttonClicked = 2;
        Toast.makeText(getApplicationContext(), "Second Reward clicked id: = " + reward2ID, Toast.LENGTH_SHORT).show();

    }
}
