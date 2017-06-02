package com.silbytech.loyali;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.silbytech.loyali.entities.CustomerRewardsListSerializable;
import com.silbytech.loyali.responses.MessageResponse;
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
    public int rewardsCount = 0;
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
    String reward1Type;
    String reward2Type;
    String vendorLogo;
    Context context = this;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_reward_layout);

        //Adds the Toolbar to the top of the Layout
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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
                                vendorLogo = customerRewardsListSerializables.get(0).getLogoTitle();

                                if(customerRewardsListSerializables.get(0).getRewardsList() != null){
                                    reward1ID = customerRewardsListSerializables.get(0).getRewardsList().get(0).getId();
                                    reward1Title.setText(customerRewardsListSerializables.get(0)
                                            .getRewardsList().get(0).getType());
                                    reward1Amount.setText(Integer.toString(customerRewardsListSerializables.get(0)
                                            .getRewardsList().get(0).getAmount()));
                                    reward1Type = customerRewardsListSerializables.get(0).getRewardsList().get(0).getType();
                                    rewardsCount = 1;
                                }
                                if(customerRewardsListSerializables.get(0).getRewardsList().size() == 2){
                                    reward2ID = customerRewardsListSerializables.get(0).getRewardsList().get(1).getId();
                                    reward2Title.setText(customerRewardsListSerializables.get(0)
                                            .getRewardsList().get(1).getType());
                                    reward2Amount.setText(Integer.toString(customerRewardsListSerializables.get(0)
                                            .getRewardsList().get(1).getAmount()));
                                    reward2Type = customerRewardsListSerializables.get(0).getRewardsList().get(1).getType();
                                    rewardsCount = 2;
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SingleRewardActivity.this);
        alertDialogBuilder.setTitle(R.string.redeemTitle);
        // set dialog message
        alertDialogBuilder
                .setMessage(R.string.redeemConfimation)
                .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                (new AsyncTask<String, Void, Void>(){
                    @Override
                    protected Void doInBackground(String... strings) {
                        Communicator communicator = new Communicator();
                        communicator.redeemRewardPOST(strings[0],
                                new Callback<MessageResponse>() {
                                    @Override
                                    public void success(MessageResponse messageResponse, Response response) {
                                        if(response.getStatus() == 200){
                                            Intent i = new Intent(SingleRewardActivity.this, RedeemConfirmationActivity.class);
                                            i.putExtra("rewardType", reward1Type);
                                            i.putExtra("vendorLogo", vendorLogo);
                                            SingleRewardActivity.this.startActivity(i);
                                            SingleRewardActivity.this.finish();
                                        }
                                    }
                                    @Override
                                    public void failure(RetrofitError error) {
                                        Toast.makeText(getApplicationContext(),
                                                R.string.connectionError, Toast.LENGTH_SHORT).show();
                                    }
                                });
                        return null;
                    }
                }).execute(Integer.toString(reward1ID));
            }
        }).setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // if this button is clicked, just close
                // the dialog box and do nothing
                dialog.cancel();
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }


    /**********************************************************************************
     * Method that wil handle the onClick of the Second (Right) reward on the screen
     * @param view - that was clicked
     **********************************************************************************/
    public void reward2OnClick(View view){
        if(rewardsCount == 2){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SingleRewardActivity.this);
            alertDialogBuilder.setTitle(R.string.redeemTitle);
            // set dialog message
            alertDialogBuilder
                    .setMessage(R.string.redeemConfimation)
                    .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    (new AsyncTask<String, Void, Void>(){
                        @Override
                        protected Void doInBackground(String... strings) {
                            Communicator communicator = new Communicator();
                            communicator.redeemRewardPOST(strings[0],
                                    new Callback<MessageResponse>() {
                                        @Override
                                        public void success(MessageResponse messageResponse, Response response) {
                                            if(response.getStatus() == 200){
                                                Intent i = new Intent(SingleRewardActivity.this, RedeemConfirmationActivity.class);
                                                i.putExtra("rewardType", reward2Type);
                                                i.putExtra("vendorLogo", vendorLogo);
                                                SingleRewardActivity.this.startActivity(i);
                                                SingleRewardActivity.this.finish();
                                            }
                                        }
                                        @Override
                                        public void failure(RetrofitError error) {
                                            Toast.makeText(getApplicationContext(),
                                                    R.string.connectionError, Toast.LENGTH_SHORT).show();

                                        }
                                    });
                            return null;
                        }
                    }).execute(Integer.toString(reward2ID));

                }
            }).setNegativeButton("No",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    dialog.cancel();
                }
            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
        else{
            Toast.makeText(getApplicationContext(),
                    R.string.noReward, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(SingleRewardActivity.this, MainMenuActivity.class);
            SingleRewardActivity.this.startActivity(i);
            finish();
        }
        switch(item.getItemId()){
            case R.id.itemProfile:
                Intent i = new Intent(SingleRewardActivity.this, UserProfileActivity.class);
                SingleRewardActivity.this.startActivity(i);
                return true;
            case R.id.itemLogout:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Logout");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                MyApplication.getInstance().clearApplicationData();
                                SharedPreferences prefs = getSharedPreferences(PREFS, 0);
                                prefs.edit().clear().apply();
                                startActivity(new Intent(SingleRewardActivity.this, LoginActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                // Create Alert Dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // Show the Dialog Box
                alertDialog.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}