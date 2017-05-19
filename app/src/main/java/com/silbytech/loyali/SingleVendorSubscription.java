package com.silbytech.loyali;

import android.app.Activity;
import android.content.Intent;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.silbytech.loyali.entities.SubscriptionSerializable;
import com.silbytech.loyali.responses.MessageResponse;
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
    public int buttonClicked = 0;
    SharedPreferences preferences;
    private int cardOneID;
    private int cardTwoID;
    private String customer_id;
    private String vendor_id;
    Activity activity = this;
    ImageView logo;
    TextView txtTitle;
    TextView txtType;
    TextView txtLocation;
    TextView txtPhone;
    TextView card1Header;
    TextView card2Header;
    GridLayout gridLayoutTop;
    GridLayout gridLayoutBottom;

    /**************************************************************************************
     * This method will make a call to the server and get all the information of the
     * specific subscription with the vendor, and will display all the details
     * @param savedInstanceState - this saved instance
     ***************************************************************************************/
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
                //Makes server call - gets the CUrrent subscription details from Server
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

                                //Displays all the information of the first Card that's in Use
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


    /*************************************************************
     * This method will override the onBackPressed so that after
     * the user has punched his card all new updated info
     * is loaded and seen in the app
     *************************************************************/
    @Override
    public void onBackPressed() {
        Intent i = new Intent(SingleVendorSubscription.this, MainMenuActivity.class);
        this.startActivity(i);
        this.finish();
    }


    /**********************************************************************************
     * Method that wil handle the onClick of the First(Top) card on the screen
     * @param view - that was clicked
     **********************************************************************************/
    public void cardOneClicked(View view){
        buttonClicked = 1;
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan QR Code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    /**********************************************************************************
     * Method that wil handle the onClick of the Second (Bottom) card on the screen
     * @param view - that was clicked
     **********************************************************************************/
    public void cardTwoClicked(View view){
        buttonClicked = 2;
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan QR Code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String cardID;
        if(buttonClicked == 1){
            cardID = Integer.toString(cardOneID);
        }
        else{
            cardID = Integer.toString(cardTwoID);
        }
        if (result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "You Cancelled Scan", Toast.LENGTH_SHORT).show();
            }
            else{
                (new AsyncTask<String, Void, Void>(){
                    @Override
                    protected Void doInBackground(String... params) {
                        Communicator communicator = new Communicator();
                        communicator.punchCard(params[0], params[1], params[2],
                                new Callback<MessageResponse>() {
                                    @Override
                                    public void success(MessageResponse messageResponse, Response response) {
                                        //Barcode Error
                                        if(response.getStatus() == 401){
                                            Toast.makeText(getApplicationContext(),
                                                    R.string.barcodeError, Toast.LENGTH_SHORT).show();
                                        }
                                        //User doesn't Exists
                                        else if(response.getStatus() == 404){
                                            Toast.makeText(getApplicationContext(),
                                                    R.string.invalidUser, Toast.LENGTH_SHORT).show();
                                        }
                                        //There is an error with that Card
                                        else if(response.getStatus() == 400){
                                            Toast.makeText(getApplicationContext(),
                                                    R.string.cardError, Toast.LENGTH_SHORT).show();
                                        }
                                        else if(response.getStatus() == 202){
                                            Toast.makeText(getApplicationContext(),
                                                    R.string.freeCoffee, Toast.LENGTH_SHORT).show();
                                        }
                                        else if(response.getStatus() == 201){
                                            Toast.makeText(getApplicationContext(),
                                                    R.string.punched, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Toast.makeText(getApplicationContext(),
                                                R.string.networkError, Toast.LENGTH_SHORT).show();
                                    }
                                });
                        return null;
                    }
                }).execute(customer_id, result.getContents(), cardID);
            }
            finish();
            startActivity(getIntent());
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
        buttonClicked = 0;
    }
}