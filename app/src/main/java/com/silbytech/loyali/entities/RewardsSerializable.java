package com.silbytech.loyali.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class RewardsSerializable implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("amount")
    private int amount;

    @SerializedName("type")
    private String type;

    @SerializedName("vendor")
    private VendorSerializable vendor;


    public RewardsSerializable(int id, int amount, String type, VendorSerializable vendor) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.vendor = vendor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VendorSerializable getVendor() {
        return vendor;
    }

    public void setVendor(VendorSerializable vendor) {
        this.vendor = vendor;
    }
}
