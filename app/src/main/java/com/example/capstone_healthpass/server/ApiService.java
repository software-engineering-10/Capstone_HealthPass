package com.example.capstone_healthpass.server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("/register/")
    Call<JSONObject> requestPost(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/login/")
    Call<JSONObject> loginPost(
            @Field("email") String email,
            @Field("password") String password
    );


    @GET("/getName")
    Call<ResponseBody> getName(
            @Query("email") String email

    );
    @FormUrlEncoded
    @POST("/reservation/")
    Call<JSONObject> reserved(
            @Field("day") String day,
            @Field("time") String time,
            @Field("email") String email,
            @Field("seat") String seat,
            @Field("ex_name") String ex_name,
            @Field("user_name") String user_name,
            @Field("user_phone") String user_phone
    );

}
