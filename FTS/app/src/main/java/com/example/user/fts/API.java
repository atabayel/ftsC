package com.example.user.fts;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface API {


    @FormUrlEncoded
    @POST("registration/new/")
    Call<ServerResponse> registration(
            @Field("name") String name,
            @Field("surname") String surname,
            @Field("email") String email,
            @Field("phone") String phone);


    @FormUrlEncoded
    @POST("registration/authentication/")
    Call<ServerResponse> authentication(
            @Field("phone") String phone);


    @Multipart
    @POST("orders/new/")
    Call<ServerResponse> newOrder(
            @Part("myid") RequestBody myid,
            @Part("language") RequestBody language,
            @Part("pages") RequestBody pages,
            @Part("urgency") RequestBody urgency,
            @Part List<MultipartBody.Part> files);


}
