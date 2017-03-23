package com.silbytech.loyali.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class VendorEntitySerializable implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("vendor")
    private VendorSerializable vendor;

    public VendorEntitySerializable(int id, VendorSerializable vendor) {
        this.id = id;
        this.vendor = vendor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VendorSerializable getVendor() {
        return vendor;
    }

    public void setVendor(VendorSerializable vendor) {
        this.vendor = vendor;
    }
}
