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

    public Card(int id, int max, String description) {
        this.id = id;
        this.max = max;
        this.description = description;
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
}
