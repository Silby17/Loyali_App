package com.silbytech.loyali;

import com.silbytech.loyali.responses.RegisterResponse;
import com.silbytech.loyali.responses.ResultResponse;
import java.util.List;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import com.silbytech.loyali.entities.VendorEntitySerializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/

/***************************************************************************
 * This Interface will define all the Posting methods that will be used
 * to send data to the web server using the POST method
 ***************************************************************************/
public interface Interface {


    /*TESTED*/
    @FormUrlEncoded
    @POST("/mobile/users/")
    void postNewUserAPI(@Field("first_name") String firstName,
                        @Field("last_name") String lastName,
                        @Field("username") String username,
                        @Field("email") String email,
                        @Field("password") String password,
                        @Field("facebook_id") String fbID,
                        @Field("push_api_key") String pushKey,
                        Callback<RegisterResponse> callback);

    /*
    @FormUrlEncoded
    @POST("/api-token-auth/")
    void loginUser(@Field("username") String username,
                   @Field("password") String password,
                   Callback<LoginToken> serverResponse);
*/

    @FormUrlEncoded
    @POST("/newFBUser")
    void postNewFBUser(@Field("name") String name,
                       @Field("userID") String userID,
                       Callback<RegisterResponse> serverResponseCallback);

    @FormUrlEncoded
    @POST("/checkDetails")
    void postCheckCreds(@Field("email") String email,
                        @Field("password") String password,
                        Callback<ResultResponse> serverResponse);


    @GET("/vendors/list/")
    void getVendorsList(@Header("Authorization") String token, Callback<List<VendorEntitySerializable>> serverResponse);

    //This will get a list of subscriptions by sending the Customer_id
    @GET("/customers/vendor-subscription/list/")
    void getSubscriptions(@Query("customer_id") int customer_id, Callback<List<VendorEntitySerializable>> serverResponse);



  /*  @FormUrlEncoded
    @POST("/getVendors")
    void postGetVendors(@Field("command") String command,
                        Callback<VendorListResponse> vendorListResponse);


    @FormUrlEncoded
    @POST("/customers/subscribe/")
    void postNewSubscription(@Field("vendor_user_id") String vendor_id,
                             @Field("customer_id") String customer_id,
                             Callback<ResultResponse> serverResponse);


    @GET("/cardsList/")
    void getCardsList(Callback<List<CardSerializable>> serverResponse);*/
}
