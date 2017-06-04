package com.silbytech.loyali;
import com.silbytech.loyali.adapters.SubscriptionListAdapter;
import com.silbytech.loyali.adapters.SubscriptionListAdapterNew;
import com.silbytech.loyali.entities.SubscriptionSerializable;
import com.silbytech.loyali.responses.MessageResponse;
import static com.facebook.FacebookSdk.getApplicationContext;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class SubscriptionListFragment extends Fragment {
    private List<SubscriptionSerializable> subscriptionList;
    private SubscriptionListAdapterNew subscriptionAdapter;
    private ListView lvSubscriptions;
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;
    String customer_id;

    public SubscriptionListFragment() {}

    public static SubscriptionListFragment newInstance() {
        Bundle args = new Bundle();
        SubscriptionListFragment fragment = new SubscriptionListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getApplicationContext().getSharedPreferences(PREFS, 0);
        //Retrieves the Customer_if from the preferences and stores it locally
        customer_id = preferences.getString("customer_id", "");
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        (new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... strings) {
                Communicator communicator = new Communicator();
                //Get all the subscriptions of the customer
                communicator.getSubscriptions(customer_id,
                        new Callback<List<SubscriptionSerializable>>() {
                            @Override
                            public void success(List<SubscriptionSerializable> subscriptionSerializables, Response response) {
                                System.out.println("Here");
                                lvSubscriptions = (ListView)getActivity().findViewById(R.id.lstViewSubscriptions);
                                subscriptionList = subscriptionSerializables;
                                subscriptionAdapter = new SubscriptionListAdapterNew(getApplicationContext(), subscriptionList);
                                lvSubscriptions.setAdapter(subscriptionAdapter);

                                lvSubscriptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        final String vendor_id = view.getTag().toString();
                                        Intent intent = new Intent(getApplicationContext(), SingleVendorSubscription.class);
                                        intent.putExtra("vendor_id", vendor_id);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });
                                //Long Item Click - Choose to remove subscription
                                lvSubscriptions.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        final String vendor_ID = view.getTag().toString();
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                        alertDialogBuilder.setTitle(R.string.deleteSubscriptionTitle);
                                        // set dialog message
                                        alertDialogBuilder
                                                .setMessage(R.string.deleteSubscription)
                                                .setCancelable(false)
                                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,int id) {

                                                        //This Async Task will Attempt to make a new subscription
                                                        // between this mobile user and the vendor fragment they
                                                        //have clicked on
                                                        (new AsyncTask<String, Void, Void>(){
                                                            @Override
                                                            protected Void doInBackground(String... params) {
                                                                Communicator communicator = new Communicator();
                                                                //Delete Subscription
                                                                communicator.deleteSubscriptionPost(params[0],
                                                                        params[1],
                                                                        new Callback<MessageResponse>() {
                                                                            @Override
                                                                            public void success(MessageResponse messageResponse,
                                                                                                Response response) {
                                                                                if(response.getStatus() == 200){
                                                                                    Toast.makeText(getApplicationContext(),
                                                                                            R.string.subDeleted, Toast.LENGTH_SHORT).show();
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
                                                        }).execute(customer_id, vendor_ID); //Delete Subscription
                                                        Intent i = new Intent(getActivity(), MainMenuActivity.class);
                                                        startActivity(i);
                                                        getActivity().finish();

                                                    }
                                                })
                                                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,int id) {
                                                        System.out.println("here");
                                                        // if this button is clicked, just close
                                                        // the dialog box and do nothing
                                                        dialog.cancel();
                                                    }
                                                });
                                        // create alert dialog
                                        AlertDialog alertDialog = alertDialogBuilder.create();

                                        // show it
                                        alertDialog.show();
                                        return true;
                                    }
                                });
                            }
                            //Failure getting the Subscriptions from the Server
                            @Override
                            public void failure(RetrofitError error) {
                                if(error.getKind().name().equals("NETWORK")){
                                    Toast.makeText(getApplicationContext(),
                                            R.string.networkError, Toast.LENGTH_SHORT).show();
                                }
                                else if(error != null){
                                    if(error.getResponse().getStatus() == 404){
                                        Toast.makeText(getApplicationContext(),
                                                R.string.invalidUser, Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),
                                                R.string.connectionError, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),
                                            R.string.connectionError, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                return null;
            }
        }).execute();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subscription_listview, container, false);
    }
}