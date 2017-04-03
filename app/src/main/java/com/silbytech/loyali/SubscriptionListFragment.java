package com.silbytech.loyali;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.silbytech.loyali.adapters.SubscriptionListAdapter;
import com.silbytech.loyali.entities.SubscriptionSerializable;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import static com.facebook.FacebookSdk.getApplicationContext;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class SubscriptionListFragment extends android.support.v4.app.Fragment {
    //Deceleration of variables
    private ListView lvSubscriptions;
    private SubscriptionListAdapter subscriptionAdapter;
    private List<SubscriptionSerializable> subscriptionList;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subscription_listview, container, false);
    }
}