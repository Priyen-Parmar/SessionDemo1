package com.session.demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLoginData {

    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("userData")
    @Expose
    public GetUserData userData;

    public class GetUserData {
        @SerializedName("userId")
        @Expose
        public String userId;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("contact")
        @Expose
        public String contact;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("profile")
        @Expose
        public String profile;
        @SerializedName("status")
        @Expose
        public String status;
    }
}
