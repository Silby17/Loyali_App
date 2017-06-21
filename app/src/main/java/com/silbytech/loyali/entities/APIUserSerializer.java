package com.silbytech.loyali.entities;

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

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;


    public APIUserSerializer(String username, int customer_id, String firstName, String lastName) {
        this.username = username;
        this.customer_id = customer_id;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
