package com.session.demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUpdateImageData {

    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("ProfileImage")
    @Expose
    public String profile;

}
