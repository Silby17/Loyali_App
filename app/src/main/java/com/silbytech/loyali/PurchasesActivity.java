package com.silbytech.loyali;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_listview);
        this.lvPurchases = (ListView)findViewById(R.id.lstViewPurchases);
        preferences = getApplicationContext().getSharedPreferences(PREFS, 0);
        //Retrieves the Customer_if from the preferences and stores it locally
        customerID = preferences.getString("customer_id", "");

        (new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... strings) {
                Communicator communicator = new Communicator();
                communicator.getCustomerPurchases(strings[0],
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
        }).execute(customerID);
    }
}