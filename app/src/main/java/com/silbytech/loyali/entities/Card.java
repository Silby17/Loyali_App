package com.silbytech.loyali.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class Card implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("max")
    private int max;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private String type;


    public Card(int id, int max, String description, String type) {
        this.id = id;
        this.max = max;
        this.description = description;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
