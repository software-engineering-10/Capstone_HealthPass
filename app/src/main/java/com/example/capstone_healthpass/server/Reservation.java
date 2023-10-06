package com.example.capstone_healthpass.server;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Field;

public class Reservation {
    @SerializedName("day")
    private String day;
    @SerializedName("time")
    private String time;
    @SerializedName("minute")
    private String minute;
    @SerializedName("email")
    private String email;
    @SerializedName("seat")
    private String seat;
    @SerializedName("ex_name")
    private String ex_name;
    @SerializedName("user_name")
    private String user_name;

    public String getUser_phone() {
        return user_phone;
    }

    @SerializedName("user_phone")
    private String user_phone;

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getMinute() {
        return minute;
    }

    public String getEmail() {
        return email;
    }

    public String getSeat() {
        return seat;
    }

    public String getEx_name() {
        return ex_name;
    }

    public String getUser_name() {
        return user_name;
    }
}
