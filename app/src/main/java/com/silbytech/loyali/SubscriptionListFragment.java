package com.silbytech.loyali;
import com.silbytech.loyali.adapters.SubscriptionListAdapter;
import com.silbytech.loyali.entities.SubscriptionSerializable;
import static com.facebook.FacebookSdk.getApplicationContext;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class SubscriptionListFragment extends android.support.v4.app.Fragment {
    private List<SubscriptionSerializable> subscriptionList;
    private SubscriptionListAdapter subscriptionAdapter;
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
        customer_id = preferences.getString("customer_id", "");
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        (new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... strings) {
                Communicator communicator = new Communicator();
                communicator.getSubscriptions(customer_id,
                        new Callback<List<SubscriptionSerializable>>() {
                            @Override
                            public void success(List<SubscriptionSerializable> subscriptionSerializables, Response response) {
                                System.out.println("Here");
                                lvSubscriptions = (ListView)getActivity().findViewById(R.id.lstViewSubscriptions);
                                subscriptionList = subscriptionSerializables;
                                subscriptionAdapter = new SubscriptionListAdapter(getApplicationContext(), subscriptionList);
                                lvSubscriptions.setAdapter(subscriptionAdapter);

                                lvSubscriptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        final String vendor_id = view.getTag().toString();
                                        Intent intent = new Intent(getApplicationContext(), SingleVendorSubscription.class);
                                        intent.putExtra("vendor_id", vendor_id);
                                        startActivity(intent);
                                    }
                                });
                            }
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