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
import android.widget.ListView;
import android.widget.Toast;
import com.silbytech.loyali.adapters.PurchaseAdapter;
import com.silbytech.loyali.entities.PurchaseSerializer;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class PurchasesActivity extends AppCompatActivity {
    private static final String TAG = ".PurchasesActivity";
    private ListView lvPurchases;
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;
    private PurchaseAdapter purchaseAdapter;
    private List<PurchaseSerializer> purchaseList;
    private String customerID;
    private String vendorID;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_listview);
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        this.lvPurchases = (ListView)findViewById(R.id.lstViewPurchases);
        this.lvPurchases.setEmptyView(findViewById(R.id.emptyPurchases));
        preferences = getApplicationContext().getSharedPreferences(PREFS, 0);
        //Retrieves the Customer_if from the preferences and stores it locally
        customerID = preferences.getString("customer_id", "");
        vendorID = getIntent().getStringExtra("vendor_id");

        (new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... strings) {
                Communicator communicator = new Communicator();
                communicator.getCustomerPurchases(strings[0], strings[1],
                        new Callback<List<PurchaseSerializer>>() {
                            @Override
                            public void success(List<PurchaseSerializer> purchaseSerializers, Response response) {
                                purchaseList = purchaseSerializers;
                                purchaseAdapter = new PurchaseAdapter(purchaseList, getApplicationContext());
                                lvPurchases.setAdapter(purchaseAdapter);
                            }
                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(getApplicationContext(),
                                        R.string.networkError, Toast.LENGTH_SHORT).show();
                            }
                        });
                return null;
            }
        }).execute(customerID, vendorID);
    }


    /*************************************************************
     * This method will override the onBackPressed so that after
     * the user has punched his card all new updated info
     * is loaded and seen in the app
     *************************************************************/
    @Override
    public void onBackPressed() {
        Intent i = new Intent(PurchasesActivity.this, MainMenuActivity.class);
        this.startActivity(i);
        this.finish();
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
            Intent i = new Intent(PurchasesActivity.this, MainMenuActivity.class);
            PurchasesActivity.this.startActivity(i);
            finish();
        }
        switch(item.getItemId()){
            case R.id.itemProfile:
                Intent i = new Intent(PurchasesActivity.this, UserProfileActivity.class);
                PurchasesActivity.this.startActivity(i);
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
                                startActivity(new Intent(PurchasesActivity.this, LoginActivity.class));
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