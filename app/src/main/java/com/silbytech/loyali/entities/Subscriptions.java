package com.silbytech.loyali.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class Subscriptions implements Serializable {

    @SerializedName("vendor_user_id")
    private int vendor_user_id;

    @SerializedName("vendor")
    private VendorSerializable vendor;

    @SerializedName("cards_in_use")
    private List<CardInUse> cardInUse;

    public Subscriptions(int vendor_user_id, VendorSerializable vendor, List<CardInUse> cardInUse) {
        this.vendor_user_id = vendor_user_id;
        this.vendor = vendor;
        this.cardInUse = cardInUse;
    }

    public int getVendor_user_id() {
        return vendor_user_id;
    }

    public void setVendor_user_id(int vendor_user_id) {
        this.vendor_user_id = vendor_user_id;
    }

    public VendorSerializable getVendor() {
        return vendor;
    }

    public void setVendor(VendorSerializable vendor) {
        this.vendor = vendor;
    }

    public List<CardInUse> getCardInUse() {
        return cardInUse;
    }

    public void setCardInUse(List<CardInUse> cardInUse) {
        this.cardInUse = cardInUse;
    }
}
