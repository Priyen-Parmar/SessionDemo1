package com.session.demo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @Multipart
    @POST("updateProfileImage.php")
    Call<GetUpdateImageData> doUpdateImage(
            @Part("firstname") RequestBody firstName,
            @Part("lastname") RequestBody lastname,
            @Part("contact") RequestBody contact,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("gender") RequestBody gender,
            @Part("city") RequestBody city,
            @Part("userid") RequestBody userid,
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("deleteProfile.php")
    Call<GetSignupData> doDelete(
            @Field("userid") String userid
    );

}
