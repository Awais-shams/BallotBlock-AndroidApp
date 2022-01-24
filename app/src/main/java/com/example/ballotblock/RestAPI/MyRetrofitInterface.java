package com.example.ballotblock.RestAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyRetrofitInterface {
    @GET("users")
    Call<List<MyRESTAPIModel>> getList();

    @GET("users/1/")
    Call<MyRESTAPIModel> getdata();
}
