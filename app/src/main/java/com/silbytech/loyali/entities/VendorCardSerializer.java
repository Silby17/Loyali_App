package com.silbytech.loyali.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class VendorCardSerializer implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("logo_title")
    private String logoTitle;

    @SerializedName("store_name")
    private String storeName;

    @SerializedName("store_type")
    private String storeType;

    @SerializedName("location")
    private String location;

    @SerializedName("phone")
    private String phone;

    @SerializedName("cards")
    private List<Card> cards;

    public VendorCardSerializer(int id, String logoTitle, String storeName, String storeType, String location, String phone, List<Card> cards) {
        this.id = id;
        this.logoTitle = logoTitle;
        this.storeName = storeName;
        this.storeType = storeType;
        this.location = location;
        this.phone = phone;
        this.cards = cards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogoTitle() {
        return logoTitle;
    }

    public void setLogoTitle(String logoTitle) {
        this.logoTitle = logoTitle;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
