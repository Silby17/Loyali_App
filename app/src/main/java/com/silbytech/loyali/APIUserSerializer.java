package com.silbytech.loyali;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class APIUserSerializer implements Serializable {

    @SerializedName("username")
    private String username;

    @SerializedName("id")
    private int customer_id;

    public APIUserSerializer(String username, int customer_id) {
        this.username = username;
        this.customer_id = customer_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}
