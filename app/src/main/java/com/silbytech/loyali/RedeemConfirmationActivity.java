package com.silbytech.loyali;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class RedeemConfirmationActivity extends Activity {
    //Deceleration of Variables
    private String MEDIA_URL = "http://192.168.137.1:8000";
    Button btnClose;
    TextView txtTitle;
    ImageView logoImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeemed_layout);

        //Assign views
        txtTitle = (TextView)findViewById(R.id.txtRewardType);
        logoImg = (ImageView)findViewById(R.id.imgVendorsLogo);
        btnClose = (Button)findViewById(R.id.btnClose);

        String type = getIntent().getStringExtra("rewardType");
        String logoURL = getIntent().getStringExtra("vendorLogo");
        String imageURL = MEDIA_URL + logoURL;
        Picasso.with(getApplicationContext()).load(imageURL).into(logoImg);
        txtTitle.setText(type);

        //Set the dimensions of the Popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * 0.8), (int)(height * .55));
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        //Set the onClick Listener for the Close button
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RedeemConfirmationActivity.this, MainMenuActivity.class);
                startActivity(i);
                RedeemConfirmationActivity.this.finish();
            }
        });
    }
}