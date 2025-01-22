package com.session.demo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signup.php")
    Call<GetSignupData> doSignup(
            @Field("firstname") String firstName,
            @Field("lastname") String lastname,
            @Field("contact") String contact,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender,
            @Field("city") String city
            );

    @FormUrlEncoded
    @POST("login.php")
    Call<GetLoginData> doLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("updateProfile.php")
    Call<GetSignupData> doUpdate(
            @Field("firstname") String firstName,
            @Field("lastname") String lastname,
            @Field("contact") String contact,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender,
            @Field("city") String city,
            @Field("userid") String userid
    );

    @FormUrlEncoded
    @POST("deleteProfile.php")
    Call<GetSignupData> doDelete(
            @Field("userid") String userid
    );

}
