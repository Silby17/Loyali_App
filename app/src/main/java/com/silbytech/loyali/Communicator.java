package com.silbytech.loyali;

import com.silbytech.loyali.responses.RegisterResponse;
import com.silbytech.loyali.responses.ResultResponse;
import com.silbytech.loyali.entities.VendorEntitySerializable;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class Communicator {
    private static final String TAG = "Communicator";
    private static final String SERVER_URL = "http://192.168.1.16:8080/";
    private static final String KROCOAPI = "http://192.168.1.96:8000/krokoapi";
    private static final String KROKO = "http://192.168.1.227:8000/kroko";

    /****************************************************************************************
     *                                                                      -----Tested------
     * This function will POST the new Mobile users information to the API
     * @param firstName - first name
     * @param lastName - last name
     * @param email - email
     * @param username - username
     * @param password - password
     * @param facebook - facebook id
     * @param pushKey - push_api_key
     * @param callback - Server callback response
     *****************************************************************************************/
    void postNewMobileUserAPI(String firstName, String lastName, String email,
                              String username, String password, String facebook,
                              String pushKey, Callback<RegisterResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(KROCOAPI)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface comInterface = restAdapter.create(Interface.class);
        comInterface.postNewUserAPI(firstName, lastName, username, email, password, facebook, pushKey, callback);
    }


    /*void userLogin(String username, String password, Callback<LoginToken> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(KROCOAPI)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface comInterface = restAdapter.create(Interface.class);
        comInterface.loginUser(username, password, callback);
    }*/



    /************************************************************************
     * This method will post the new FB users details to the server
     * @param name - users name
     * @param userID - users ID
     * @param callback - callback response
     ************************************************************************/
     void newFBUserPost(String name,
                              String userID,
                              Callback<RegisterResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface comInterface = restAdapter.create(Interface.class);
        comInterface.postNewFBUser(name, userID, callback);
    }


    void checkUserLogin(String email, String password,
                        Callback<ResultResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface comInterface = restAdapter.create(Interface.class);
        comInterface.postCheckCreds(email, password, callback);
    }



  /*  public void postGetVendors(String command, Callback<VendorListResponse> vendorListResponse){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface comInterface = restAdapter.create(Interface.class);
        comInterface.postGetVendors(command, vendorListResponse);
    }*/



   /* public void postNewSubscription(String vendor_id, String customer_id,
                                    Callback<ResultResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface comInterface = restAdapter.create(Interface.class);
        comInterface.postNewSubscription(vendor_id, customer_id, callback);
    }*/


/*
    public void getVendorList(String token, Callback<List<VendorEntitySerializable>> serverResponse){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(KROCOAPI)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface comInterface = restAdapter.create(Interface.class);
        comInterface.getVendorsList(token, serverResponse);
    }


    public void getCardsList(Callback<List<CardSerializable>> serverResponse){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(KROKO)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface interfce = restAdapter.create(Interface.class);
        interfce.getCardsList(serverResponse);
    }

    void getSubscriptions(int customer_id, Callback<List<VendorEntitySerializable>> serverResponse){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(KROCOAPI)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface interfce = restAdapter.create(Interface.class);
        interfce.getSubscriptions(customer_id, serverResponse);
    }*/
}
