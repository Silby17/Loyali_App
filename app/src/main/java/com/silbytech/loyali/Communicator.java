package com.silbytech.loyali;
import com.silbytech.loyali.communication.Interface;
import com.silbytech.loyali.entities.CustomerRewardsListSerializable;
import com.silbytech.loyali.entities.PurchaseSerializer;
import com.silbytech.loyali.entities.SubscriptionSerializable;
import com.silbytech.loyali.entities.VendorCardSerializer;
import com.silbytech.loyali.responses.MessageResponse;
import com.silbytech.loyali.responses.RegisterResponse;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class Communicator {
    private static final String TAG = "Communicator";
    private static final String LOYALI_API = "http://192.168.1.14:8000/loyaliapi";


    /****************************************************************************************
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
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface comInterface = restAdapter.create(Interface.class);
        comInterface.postNewUserAPI(firstName, lastName, username, email, password, facebook, pushKey, callback);
    }


    /****************************************************************************************
     * This Method will send the users login details to the server for authentication
     * @param username - username
     * @param password - password
     * @param callback - server response
     ****************************************************************************************/
    void userLogin(String username, String password, Callback<RegisterResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface comInterface = restAdapter.create(Interface.class);
        comInterface.loginUser(username, password, callback);
    }


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
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface comInterface = restAdapter.create(Interface.class);
        comInterface.postNewFBUser(name, userID, callback);
    }


    /****************************************************************************************
     * This will GET a list of all vendors together with a list of the cards that they
     * offer
     * @param serverResponse - the server response
     ****************************************************************************************/
    void getVendorsWithCards(Callback<List<VendorCardSerializer>> serverResponse){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface inter = restAdapter.create(Interface.class);
        inter.getVendorsWithCards(serverResponse);
    }


    /****************************************************************************************
     * This Method will POST the details ofa new subscription to the server
     * @param vendor_id - vendor ID
     * @param customer_id - Customer ID
     * @param callback - the servers response
     *****************************************************************************************/
    void postCreateSubscription(String vendor_id, String customer_id,
                                       Callback<MessageResponse> callback) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface comInterface = restAdapter.create(Interface.class);
        comInterface.postCreateSubscription(vendor_id, customer_id, callback);
    }


    /****************************************************************************************
     * This will post the necessary details in order to delete a subscription
     * @param customerID - customers ID
     * @param vendorID - vendors D
     * @param callback - the server response
     *****************************************************************************************/
    void deleteSubscriptionPost(String customerID, String vendorID,
                                Callback<MessageResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface comInterface = restAdapter.create(Interface.class);
        comInterface.deleteSubscriptionPost(customerID, vendorID, callback);
    }


    /****************************************************************************************
     * This will GET all the current users subscriptions
     * @param customer_id - current customers ID
     * @param serverResponse - response from server
     *****************************************************************************************/
    void getSubscriptions(String customer_id, Callback<List<SubscriptionSerializable>> serverResponse){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface inter = restAdapter.create(Interface.class);
        inter.getSubscriptions(customer_id, serverResponse);
    }


    /****************************************************************************************
     * This will get all the information of a specific subscription of the customer
     * @param customer_id - customers ID
     * @param vendor_id - vendors ID
     * @param serverResponse - server response
     *****************************************************************************************/
    void getSubscriptionCardsByVendorID(String customer_id, String vendor_id,
                                        Callback<List<SubscriptionSerializable>> serverResponse){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface inter = restAdapter.create(Interface.class);
        inter.getSubscriptionCardsByVendorID(customer_id, vendor_id, serverResponse);
    }


    /****************************************************************************************
     * This will post the necessary information to the server to "punch" a certain card
     * @param customerID - customer ID
     * @param barcode - barcode Scanned
     * @param cardID - Card ID to be punched
     * @param serverResponse - server response
     *****************************************************************************************/
    void punchCard(String customerID, String barcode, String cardID,
                   Callback<MessageResponse> serverResponse){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface inter = restAdapter.create(Interface.class);
        inter.punchCardPOST(customerID, barcode, cardID, serverResponse);
    }


    /****************************************************************************************
     * This will GET all the rewards of the current user
     * @param customerID - customers ID
     * @param serverResponse - server response
     ****************************************************************************************/
    void getRewards(String customerID, Callback<List<CustomerRewardsListSerializable>> serverResponse){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface inter = restAdapter.create(Interface.class);
        inter.getRewards(customerID, serverResponse);
    }


    /****************************************************************************************
     * This will get all the info of the Vendor and the rewards relating to that vendor
     * @param customerID - the customers ID
     * @param vendorID - vendors ID
     * @param serverResponse - server response
     ****************************************************************************************/
    void getRewardsByVendor(String customerID, String vendorID,
                            Callback<List<CustomerRewardsListSerializable>> serverResponse){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface inter = restAdapter.create(Interface.class);
        inter.getRewardsByVendor(customerID, vendorID, serverResponse);
    }


    /****************************************************************************************
     * POST to the server in order to redeem a reward
     * @param rewardID - reward ID to be redeemed
     * @param serverResponse - server response
     ****************************************************************************************/
    void redeemRewardPOST(String rewardID, Callback<MessageResponse> serverResponse){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface inter = restAdapter.create(Interface.class);
        inter.redeemRewardPOST(rewardID, serverResponse);
    }


    /****************************************************************************************
     * Gets a list of all the customers purchases
     * @param customerID - customer ID
     * @param vendorID - vendors ID (optional value)
     * @param serverResponse - server response
     ****************************************************************************************/
    void getCustomerPurchases(String customerID, String vendorID,
                              Callback<List<PurchaseSerializer>> serverResponse){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(LOYALI_API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface inter = restAdapter.create(Interface.class);
        inter.getPurchases(customerID, vendorID, serverResponse);
    }
}