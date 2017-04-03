package com.silbytech.loyali;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.silbytech.loyali.entities.SubscriptionSerializable;


import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class TesterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        (new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... strings) {
                Communicator communicator = new Communicator();
                communicator.getSubscriptions("8",
                        new Callback<List<SubscriptionSerializable>>() {
                            @Override
                            public void success(List<SubscriptionSerializable> subscriptionses, Response response) {
                                System.out.println("here");
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                System.out.println("Failed");

                            }
                        });
                return null;
            }
        }).execute();


    }
}
