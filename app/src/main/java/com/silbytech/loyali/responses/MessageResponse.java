package com.silbytech.loyali.responses;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class MessageResponse implements Serializable {

    @SerializedName("message")
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
