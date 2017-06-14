package com.silbytech.loyali.entities;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class PurchaseSerializer implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("vendor")
    private VendorSerializable vendor;

    @SerializedName("type")
    private String type;

    @SerializedName("date")
    private String date;

    public PurchaseSerializer(int id, VendorSerializable vendor, String type, String date) {
        this.id = id;
        this.vendor = vendor;
        this.type = type;
        this.date = date;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
