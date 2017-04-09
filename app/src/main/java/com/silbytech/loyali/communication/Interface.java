package com.silbytech.loyali.communication;

import com.silbytech.loyali.entities.SubscriptionSerializable;
import com.silbytech.loyali.entities.VendorCardSerializer;
import com.silbytech.loyali.responses.MessageResponse;
import com.silbytech.loyali.responses.RegisterResponse;
import com.silbytech.loyali.responses.ResultResponse;
import java.util.List;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

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



    @GET("/mobile/vendorsAndCards/") /*Working and tested*/
    void getVendorsWithCards(Callback<List<VendorCardSerializer>> serverResponse);


    @GET("/mobile/subscriptions/")
    void getSubscriptions(@Query("customer_id") String customer_id, Callback<List<SubscriptionSerializable>> serverResponse);


    @FormUrlEncoded
    @POST("/createSubscription/")
    void postCreateSubscription(@Field("vendor_id") String vendor_id,
                                @Field("customer_id") String customer_id,
                                Callback<MessageResponse> serverResponse);


    @GET("/mobile/subscriptions/byCardID/")
    void getSubscriptionCardsByVendorID(@Query("customer_id") String customer_id,
                                        @Query("vendor_id") String vendor_id,
                                        Callback<List<SubscriptionSerializable>> serverResponse);

  /*  @FormUrlEncoded
    @POST("/getVendors")
    void postGetVendors(@Field("command") String command,
                        Callback<VendorListResponse> vendorListResponse);





    @GET("/cardsList/")
    void getCardsList(Callback<List<CardSerializable>> serverResponse);*/
}
