package com.silbytech.loyali.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class CustomerRewardsListSerializable implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("store_name")
    private String storeName;

    @SerializedName("location")
    private String location;

    @SerializedName("store_type")
    private String storeType;

    @SerializedName("logo_title")
    private String logoTitle;

    @SerializedName("phone")
    private String phone;

    @SerializedName("rewards")
    private List<RewardSerializable> rewardsList;

    public CustomerRewardsListSerializable(int id, String storeName, String location,
                                           String storeType, String logoTitle, String phone,
                                           List<RewardSerializable> rewardsList) {
        this.id = id;
        this.storeName = storeName;
        this.location = location;
        this.storeType = storeType;
        this.logoTitle = logoTitle;
        this.phone = phone;
        this.rewardsList = rewardsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getLogoTitle() {
        return logoTitle;
    }

    public void setLogoTitle(String logoTitle) {
        this.logoTitle = logoTitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<RewardSerializable> getRewardsList() {
        return rewardsList;
    }

    public void setRewardsList(List<RewardSerializable> rewardsList) {
        this.rewardsList = rewardsList;
    }
}
