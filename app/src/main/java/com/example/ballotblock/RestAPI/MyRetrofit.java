package com.example.ballotblock.RestAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {
    public static Retrofit retrofit;
//    1. If u are running with emulator use URL as http://10.0.2.2:8080/ instead of localhost
//    2. Running from mobile app use PC IP address
    public static String uRL = "http://10.20.16.206:3001";
//    public static String uRL = "https://ballotblock.herokuapp.com/";

    public static Retrofit getRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(uRL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofit2() {

        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(uRL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
