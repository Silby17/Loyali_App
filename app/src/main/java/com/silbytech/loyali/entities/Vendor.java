package com.silbytech.loyali.entities;

/************************************
 * Created by Yosef Silberhaft
 ************************************/

public class Vendor {
    //Deceleration of variables
    private int id;
    private String storeName;
    private String location;
    private int phone;
    private String logoTitle;
    private String storeType;
    private String email;


    /*************************************************************************
     * Constructor Method
     * @param id - vendor id
     * @param storeName - store name
     * @param location - location
     * @param phone - phone number
     * @param logoTitle - logo title
     * @param storeType - store type
     * @param email - email address
     *************************************************************************/
    public Vendor(int id, String storeName, String location,
                  int phone, String logoTitle,
                  String storeType, String email) {

        this.id = id;
        this.storeName = storeName;
        this.location = location;
        this.phone = phone;
        this.logoTitle = logoTitle;
        this.storeType = storeType;
        this.email = email;
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getLogoTitle() {
        return logoTitle;
    }

    public void setLogoTitle(String logoTitle) {
        this.logoTitle = logoTitle;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}