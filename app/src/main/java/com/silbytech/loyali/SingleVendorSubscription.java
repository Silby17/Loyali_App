package com.silbytech.loyali;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.silbytech.loyali.entities.SubscriptionSerializable;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class SingleVendorSubscription extends AppCompatActivity {
    private String TAG = "SingleVendorSubscription";
    private String MEDIA_URL = "http://192.168.137.1:8000";
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;
    private int cardOneID;
    private int cardTwoID;
    private String customer_id;
    private String vendor_id;
    ImageView logo;
    TextView txtTitle;
    TextView txtType;
    TextView txtLocation;
    TextView txtPhone;
    TextView card1Header;
    TextView card2Header;
    GridLayout gridLayoutTop;
    GridLayout gridLayoutBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_subscription_fragment);
        logo = (ImageView)findViewById(R.id.imgVendorLogo);
        txtTitle = (TextView)findViewById(R.id.txtVendorTitle);
        txtType = (TextView)findViewById(R.id.txtVendorType);
        txtLocation = (TextView)findViewById(R.id.txtVendorLocation);
        txtPhone = (TextView)findViewById(R.id.txtVendorPhone);
        card1Header = (TextView)findViewById(R.id.card1Header);
        card2Header = (TextView)findViewById(R.id.card2Header);
        gridLayoutTop = (GridLayout)findViewById(R.id.gridLayoutTop);
        gridLayoutBottom = (GridLayout)findViewById(R.id.gridLayoutBottom);
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        preferences = getApplicationContext().getSharedPreferences(PREFS, 0);
        this.customer_id = preferences.getString("customer_id", "");
        this.vendor_id = getIntent().getStringExtra("vendor_id");

        (new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... strings) {
                Communicator communicator = new Communicator();
                communicator.getSubscriptionCardsByVendorID(customer_id, vendor_id,
                        new Callback<List<SubscriptionSerializable>>() {
                            @Override
                            public void success(List<SubscriptionSerializable> subscriptionSerializables, Response response) {
                                String imageURL = MEDIA_URL + subscriptionSerializables.get(0).getVendor().getLogoTitle();
                                Picasso.with(getApplicationContext()).load(imageURL).into(logo);
                                txtTitle.setText(subscriptionSerializables.get(0).getVendor().getStoreName());
                                txtType.setText(subscriptionSerializables.get(0).getVendor().getStoreType());
                                txtPhone.setText(subscriptionSerializables.get(0).getVendor().getPhone());
                                txtLocation.setText(subscriptionSerializables.get(0).getVendor().getLocation());

                                /**Displays all the information of the first Card that's in Use**/
                                if(subscriptionSerializables.get(0).getCardInUse().size() != 0){
                                    cardOneID = subscriptionSerializables.get(0).getCardInUse().get(0).getId();
                                    card1Header.setText(subscriptionSerializables.get(0).getCardInUse().get(0).getCard().getDescription());
                                    int card1Max = subscriptionSerializables.get(0).getCardInUse().get(0).getCard().getMax();
                                    int card1Current = subscriptionSerializables.get(0).getCardInUse().get(0).getCurrent();
                                    ImageView img;
                                    int i;
                                    for(i = 0; i < card1Current; i++){
                                        img = new ImageView(getApplicationContext());
                                        String redLogoURL = MEDIA_URL + "/media/loyali_logo.png";
                                        Picasso.with(getApplicationContext()).load(redLogoURL).into(img);
                                        gridLayoutTop.addView(img, 200, 200);
                                    }
                                    for(int j = i; j < card1Max; j++){
                                        img = new ImageView(getApplicationContext());
                                        //img.setImageResource(R.drawable.logo_grey_compressed);
                                        String greyLogoURL = MEDIA_URL + "/media/logo_grey_compressed.png/";
                                        Picasso.with(getApplicationContext()).load(greyLogoURL).into(img);
                                        gridLayoutTop.addView(img, 200, 200);
                                    }
                                    if(subscriptionSerializables.get(0).getCardInUse().size() == 2){
                                        cardTwoID = subscriptionSerializables.get(0).getCardInUse().get(1).getId();
                                        card2Header.setText(subscriptionSerializables.get(0).getCardInUse().get(1).getCard().getDescription());
                                        int card2Max = subscriptionSerializables.get(0).getCardInUse().get(1).getCard().getMax();
                                        int card2Current = subscriptionSerializables.get(0).getCardInUse().get(1).getCurrent();
                                        ImageView img2;
                                        int m;
                                        for(m = 0; m < card2Current; m++){
                                            img2 = new ImageView(getApplicationContext());
                                            String redLogoURL = MEDIA_URL + "/media/loyali_logo.png";
                                            Picasso.with(getApplicationContext()).load(redLogoURL).into(img2);
                                            gridLayoutBottom.addView(img2, 200, 200);
                                        }
                                        for(int j = i; j < card2Max; j++){
                                            img2 = new ImageView(getApplicationContext());
                                            String greyLogoURL = MEDIA_URL + "/media/logo_grey_compressed.png/";
                                            Picasso.with(getApplicationContext()).load(greyLogoURL).into(img2);
                                            gridLayoutBottom.addView(img2, 200, 200);
                                        }
                                    }
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
        }).execute();
    }

    public void cardOneClicked(View view){
        Toast.makeText(getApplicationContext(),
                "Card one Clicked with ID: " + Integer.toString(cardOneID), Toast.LENGTH_SHORT).show();
        //Send Card QR code to the server - Server side will check code and if correct
        // increase the current card and send back a 200 Response
    }


    public void cardTwoClicked(View view){
        Toast.makeText(getApplicationContext(),
                "Card Two Clicked with ID: " + Integer.toString(cardTwoID), Toast.LENGTH_SHORT).show();
    }
}