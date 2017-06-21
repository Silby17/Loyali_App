package com.silbytech.loyali.responses;

import com.google.gson.annotations.SerializedName;
import com.silbytech.loyali.entities.APIUserSerializer;

import java.io.Serializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/

public class RegisterResponse implements Serializable {

    @SerializedName("user_result")
    private APIUserSerializer apiUser;

    public RegisterResponse(String token, APIUserSerializer apiUser) {
        this.apiUser = apiUser;
    }

    public APIUserSerializer getApiUser() {
        return apiUser;
    }

    public void setApiUser(APIUserSerializer apiUser) {
        this.apiUser = apiUser;
    }
}
