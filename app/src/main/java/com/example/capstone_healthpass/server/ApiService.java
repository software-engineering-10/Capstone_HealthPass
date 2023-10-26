package com.example.capstone_healthpass.server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
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
            @Field("minute")String minute,
            @Field("email") String email,
            @Field("seat") String seat,
            @Field("ex_name") String ex_name,
            @Field("user_name") String user_name,
            @Field("user_phone") String user_phone
    );
    @FormUrlEncoded
    @POST("time/")
    Call<JSONObject> reservedTime(
            @Field("day") String day,
            @Field("time") String time,
            @Field("minute")String minute,
            @Field("email") String email

    );
    @FormUrlEncoded
    @POST("machine/")
    Call<JSONObject> reservedMachine(
            @Field("day") String day,
            @Field("time") String time,
            @Field("minute")String minute,
            @Field("seat") String seat,
            @Field("ex_name") String ex_name

    );
    @FormUrlEncoded
    @POST("info/")
    Call<List<Reservation>> reservedInfo(
            @Field("email") String email

    );
    @DELETE("info/")
    Call<ResponseBody> reservedCancel(
            @Query("day") String day,
            @Query("time") String time,
            @Query("minute")String minute,
            @Query("seat") String seat,
            @Query("ex_name") String ex_name

    );


}
