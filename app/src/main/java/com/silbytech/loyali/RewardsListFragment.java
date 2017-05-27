package com.silbytech.loyali;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.silbytech.loyali.adapters.RewardsAdapter;
import com.silbytech.loyali.entities.CustomerRewardsListSerializable;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import static com.facebook.FacebookSdk.getApplicationContext;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class RewardsListFragment extends Fragment {
    private List<CustomerRewardsListSerializable> rewardsList;
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;
    private RewardsAdapter rewardsAdapter;
    private ListView rewardsListView;
    private String customerID;

    public RewardsListFragment() {}

    public static RewardsListFragment newInstance() {
        Bundle args = new Bundle();
        RewardsListFragment fragment = new RewardsListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getApplicationContext().getSharedPreferences(PREFS, 0);
        customerID = preferences.getString("customer_id", "");
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        (new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... strings) {
                Communicator communicator = new Communicator();
                communicator.getRewards(strings[0],
                        new Callback<List<CustomerRewardsListSerializable>>() {
                            @Override
                            public void success(List<CustomerRewardsListSerializable> rewardsSerializables, Response response) {
                                rewardsListView = (ListView)getActivity().findViewById(R.id.lstViewRewards);
                                rewardsList = rewardsSerializables;
                                rewardsAdapter = new RewardsAdapter(rewardsList, getApplicationContext());
                                rewardsListView.setAdapter(rewardsAdapter);

                               rewardsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                   @Override
                                   public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                       //Gets the Vendor ID from the View Tag
                                       final String vendor_id = view.getTag().toString();
                                       Intent intent = new Intent(getApplicationContext(), SingleRewardActivity.class);
                                       //Passes the Vendor ID to the intent
                                       intent.putExtra("vendor_id", vendor_id);
                                       startActivity(intent);
                                       //Removes the Intent from the stack
                                       getActivity().finish();
                                   }
                               });
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(getApplicationContext(),
                                        R.string.connectionError, Toast.LENGTH_SHORT).show();
                            }
                        });
                return null;
            }
        }).execute(customerID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rewards_listview, container, false);
    }
}
