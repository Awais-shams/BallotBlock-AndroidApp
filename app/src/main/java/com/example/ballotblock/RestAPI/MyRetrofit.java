package com.example.ballotblock.RestAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {
    public static Retrofit retrofit;
    public static String uRL = "https://jsonplaceholder.typicode.com/";

    public static Retrofit getRetrofit() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(uRL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
