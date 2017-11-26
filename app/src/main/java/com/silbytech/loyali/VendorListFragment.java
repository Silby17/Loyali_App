package com.silbytech.loyali;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.silbytech.loyali.adapters.VendorListAdapter;
import com.silbytech.loyali.entities.VendorCardSerializer;
import com.silbytech.loyali.responses.MessageResponse;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import static com.facebook.FacebookSdk.getApplicationContext;


/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class VendorListFragment extends android.support.v4.app.Fragment {
    //Deceleration of Variables
    private List<VendorCardSerializer> vendorCardsList;
    public static final String PREFS = "prefs";
    private VendorListAdapter vendorListAdapter;
    private SharedPreferences preferences;
    private ListView lvVendors;
    String customer_id;

    public VendorListFragment() {}


    public static VendorListFragment newInstance(){
        VendorListFragment fragment = new VendorListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getApplicationContext().getSharedPreferences(PREFS, 0);
        customer_id = preferences.getString("customer_id", "");
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //This Async Task will get all the vendors from the Server and will pass all the info
        // into the correct adapter
        (new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... strings) {
                final Communicator communicator = new Communicator();
                //Server Call to get a list of all vendors and their Card details
                communicator.getVendorsWithCards(new Callback<List<VendorCardSerializer>>() {

                    @Override
                    public void success(List<VendorCardSerializer> vendorCardSerializers, Response response) {
                        lvVendors = (ListView)getView().findViewById(R.id.lstViewVendors);
                        vendorCardsList = vendorCardSerializers;
                        vendorListAdapter = new VendorListAdapter(getApplicationContext(), vendorCardsList);
                        lvVendors.setAdapter(vendorListAdapter);

                        /****************************************************************************
                         * On long click of message the user will be prompted to add the current
                         * vendor to their favourites
                         ****************************************************************************/
                        lvVendors.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                final String vendor_id = view.getTag().toString();
                                final int intVendorID = Integer.parseInt(vendor_id);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                alertDialogBuilder.setTitle(R.string.add_to_favourites);

                                // set dialog message
                                alertDialogBuilder
                                        .setMessage(R.string.save_to_favourites)
                                        .setCancelable(false)
                                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                //This Async Task will Attempt to make a new subscription
                                                // between this mobile user and the vendor fragment they
                                                //have clicked on
                                                (new AsyncTask<String, Void, Void>(){
                                                    @Override
                                                    protected Void doInBackground(String... strings) {
                                                        Communicator com = new Communicator();
                                                        //Create Subscription
                                                        com.postCreateSubscription(strings[0], strings[1],
                                                                new Callback<MessageResponse>() {
                                                                    @Override
                                                                    public void success(MessageResponse messageResponse, Response response) {
                                                                        Toast.makeText(getApplicationContext(),
                                                                                messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        SplashActivity.pubnubController.RegisterVendorChannel(intVendorID);
                                                                    }
                                                                    //Failure creating subscription
                                                                    @Override
                                                                    public void failure(RetrofitError error) {
                                                                        String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                                                                        String replaced = json.replaceAll("[{}]", "");
                                                                        String[] msg = replaced.split(":");
                                                                        Toast.makeText(getApplicationContext(),
                                                                                msg[1], Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                        return null;
                                                    }
                                                }).execute(vendor_id, customer_id); //Create Subscription
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
                    //Failure getting list of Vendors from the Server
                    @Override
                    public void failure(RetrofitError error) {
                        if(error.getKind().name().equals("NETWORK")){
                            Toast.makeText(getApplicationContext(),
                                    R.string.networkError, Toast.LENGTH_SHORT).show();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vendor_listview, container, false);
    }
}