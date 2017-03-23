package com.silbytech.loyali.responses;

import com.google.gson.annotations.SerializedName;
import com.silbytech.loyali.APIUserSerializer;

import java.io.Serializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/

public class RegisterResponse implements Serializable {

    @SerializedName("token")
    private String token;

    @SerializedName("user_result")
    private APIUserSerializer apiUser;

    public RegisterResponse(String token, APIUserSerializer apiUser) {
        this.token = token;
        this.apiUser = apiUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public APIUserSerializer getApiUser() {
        return apiUser;
    }

    public void setApiUser(APIUserSerializer apiUser) {
        this.apiUser = apiUser;
    }
}
